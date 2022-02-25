/*
 * FofaLineTableModel.java
 * @author: aw220
 * @date: 2022/2/21 下午3:45
 *
 *
 */

package burp.ui.model;

import burp.action.parser.FofaParser;
import burp.ui.entry.FofaLineEntry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static burp.Utils.*;
import static burp.Utils.stdout;
import static org.apache.logging.log4j.util.Strings.isBlank;


public class FofaLineTableModel extends AbstractTableModel implements Serializable {
    //https://stackoverflow.com/questions/11553426/error-in-getrowcount-on-defaulttablemodel
    //when use DefaultTableModel, getRowCount encounter NullPointerException. why?
    /**
     * LineTableModel中数据如果类型不匹配，或者有其他问题，可能导致图形界面加载异常！
     */
    private static final long serialVersionUID = 1L;
    private FofaLineEntry currentlyDisplayedItem;
    // 线程池
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4,
            new BasicThreadFactory.Builder().namingPattern("task-schedule-pool-%d").daemon(true).build());

    private List<FofaLineEntry> lineEntries = new ArrayList<>();
    private static final List<String> titletList = FofaLineEntry.fetchTableHeaderList();

    //为了实现动态表结构
    public static List<String> getTitletList() {
        return titletList;
    }

    public FofaLineTableModel() {
    }

    public void search(int page, String query) {
        if (!isBlank(query)) {
            executorService.schedule(() -> {
                int retryCount = RETRY_COUNT;
                while (retryCount-- != 0) {
                    JSONObject fofaData;
                    HashMap<String, String> temp;
                    try {
                        temp = client.getData(query, page, 5000, 5000);
                        fofaData = JSON.parseObject(temp.get("msg"));
                        if (temp.get("code").equals("200") && !fofaData.getJSONArray("results").isEmpty()) {
                            FOFA_ONEPAGE_SIZE += 20;// 奖励，增加20

                            // fofaData --> {"mode":"","size":1,"query":"","page":1,"error":false,"results":[[]]}
                            FOFA_TOTAL_SIZE = (Integer) fofaData.get("size");
                            // 将返回数据丢进result保存，后面新增的自然是要存在List的后面
                            fofaResult.addAll(FofaParser.fromArray(fofaData.getJSONArray("results")));
//                            totalTablePage = fofaResult.size() % TABLE_ONEPAGE_SIZE == 0 && fofaResult.size() != 0 ? fofaResult.size() / TABLE_ONEPAGE_SIZE : fofaResult.size() / TABLE_ONEPAGE_SIZE + 1;
                            stdout.println("Current Thread" + Thread.currentThread().getName());
//                            if (!autoSearch)
                            break;
                        } else {
                            if (FOFA_ONEPAGE_SIZE > 20)
                                FOFA_ONEPAGE_SIZE -= 20;// 惩罚，减少20
                            if (retryCount == 0) {
                                pageLabelHelper.setText("Try Again!");
                                if (page > 1)
                                    FOFA_PAGE.set(page - 1);// 搜索失败，页数不变
                            }
                            // 请求失败时 等待1s再次请求
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace(stderr);
                            }
                        }
                    } catch (Exception ex) {
                        try {
                            Thread.sleep(300);// 给pageLabel文字反馈时间，增强交互
                        } catch (InterruptedException e) {
                            e.printStackTrace(stderr);
                        }
                        ex.printStackTrace(stderr);

                        FOFA_PAGE.set(page - 1);// 搜索失败，页数不变
                        pageLabelHelper.setText("Error");
                        break;
                    }
                }
                latch.countDown();
            }, 0, TimeUnit.SECONDS);
        }
    }

    // 自动加载数据，因为FOFA网速太慢了。。。
    public void autoSearch(int page, String query) {
        if (fofaResult.size() - 40 < page * TABLE_ONEPAGE_SIZE) {
            stdout.println("autoSearch：start");
            search(page, query);
            stdout.println("autoSearch：over");
        }
    }


    public List<FofaLineEntry> getLineEntries() {
        return lineEntries;
    }

    public void setLineEntries(List<FofaLineEntry> lineEntries) {
        this.lineEntries = lineEntries;
    }

    public void addLineEntries(List<FofaLineEntry> lineEntries) {
        this.lineEntries.addAll(lineEntries);
    }

    public void clear() {
        int rows = this.getRowCount();
        this.setLineEntries(new ArrayList<>());
        if (rows > 0) fireTableRowsDeleted(0, rows - 1);
    }

    ////////////////////// extend AbstractTableModel////////////////////////////////

    @Override
    public int getColumnCount() {
        return titletList.size();//the one is the request String + response String,for search
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        if (columnIndex == titletList.indexOf("#")) {
            return Integer.class;//id
        }
        return String.class;
    }

    @Override
    public int getRowCount() {
        return lineEntries.size();
    }

    //define header of table???
    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < titletList.size()) {
            return titletList.get(columnIndex);
        } else {
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (titletList.get(columnIndex).equals("cert") || titletList.get(columnIndex).equals("header")) {

            return true;
        } else return false;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FofaLineEntry entries = lineEntries.get(rowIndex);
        if (entries == null) return "";
        if (columnIndex == titletList.indexOf("#")) {
            return rowIndex;
        }
        try {
            return entries.fetchValue(FofaLineEntry.fetchTableHeaderList().get(columnIndex));
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(stderr);
            return "error";
        }
    }


    @Override
    public void setValueAt(Object value, int row, int col) {
    }
    //////////////////////extend AbstractTableModel////////////////////////////////

    public String getStatusSummary() {
        int all = lineEntries.size();
        return String.format(" [%s results in total]", all);
    }


    public FofaLineEntry getCurrentlyDisplayedItem() {
        return this.currentlyDisplayedItem;
    }

    public void setCurrentlyDisplayedItem(FofaLineEntry currentlyDisplayedItem) {
        this.currentlyDisplayedItem = currentlyDisplayedItem;
    }
}