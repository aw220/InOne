/*
 * FofaPanel.java
 * @author: aw220
 * @date: 2022/2/25 下午11:45
 *
 *
 */

/*
 * FofaPanel.java
 * @author: aw220
 * @date: 2022/2/21 下午5:53
 *
 *
 */

package burp.ui.panel;

import burp.ui.entry.FofaLineEntry;
import burp.ui.model.FofaLineTableModel;
import burp.ui.table.FofaLineTable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static burp.Utils.*;

public class FofaPanel extends JPanel {
    private JPanel btnPanel;
    private JPanel tablePanel;
    private JPanel btnFirstLinePanel;
    private JPanel btnSecondLinePanel;
    private JPanel pagePanel;
    private FofaLineTable resultTable;
    private FofaLineTableModel fofaLineTableModel = new FofaLineTableModel();
    private JLabel searchLabel;
    private JLabel totalSizeLabel;
    private JLabel pageLabel;
    private JLabel tableSizeLabel;
    private JButton searchBtn;
    private JButton exportBtn;
    private JButton prePageBtn;
    private JButton nextPageBtn;
    private JTextField searchField;
    private JTextField tablePageSizeField;
    private JCheckBox autoSearchBox;
    private JCheckBox tableHeadCert;
    private JCheckBox tableHeadHeader;
    private JSeparator sep1;

    public FofaPanel() {
        init();
        initListener();
    }

    private void init() {

        pagePanel = new JPanel();
        btnPanel = new JPanel();
        tablePanel = new JPanel();
        btnFirstLinePanel = new JPanel();
        btnSecondLinePanel = new JPanel();
        searchLabel = new JLabel();
        totalSizeLabel = new JLabel();
        pageLabel = new JLabel();
        tableSizeLabel = new JLabel();
        searchBtn = new JButton();
        exportBtn = new JButton();
        prePageBtn = new JButton();
        nextPageBtn = new JButton();
        searchField = new JTextField();
        tablePageSizeField = new JTextField();
        searchBoxHelper = searchField;
        pageLabelHelper = pageLabel;
        resultTable = new FofaLineTable(fofaLineTableModel);
        sep1 = new JSeparator(SwingConstants.VERTICAL);

        this.setLayout(new BorderLayout(5, 5));
        this.setSize(500, 500);

        //////////////// btnPanel ////////////////
        {
            btnPanel.setLayout(new BorderLayout());
            //////////////// btnFirstLinePanel ////////////////
            {
                btnFirstLinePanel.setLayout(new GridBagLayout());
                ((GridBagLayout) btnFirstLinePanel.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).rowHeights = new int[]{0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).rowWeights = new double[]{0.0};
                //-+-+-+-+-+-+-+-+-+-+ searchLabel -+-+-+-+-+-+-+-+-+-+
                searchLabel.setText("Rule:");
                btnFirstLinePanel.add(searchLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ searchBox -+-+-+-+-+-+-+-+-+-+
                btnFirstLinePanel.add(searchField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ totalSizeLabel -+-+-+-+-+-+-+-+-+-+
                totalSizeLabel.setText("Total Size: " + FOFA_TOTAL_SIZE);
                btnFirstLinePanel.add(totalSizeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ searchBtn -+-+-+-+-+-+-+-+-+-+
                searchBtn.setText("Search");
                btnFirstLinePanel.add(searchBtn, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ exportBtn -+-+-+-+-+-+-+-+-+-+
                exportBtn.setText("Export");
                btnFirstLinePanel.add(exportBtn, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));

                btnPanel.add(btnFirstLinePanel, BorderLayout.NORTH);
            }
            //////////////// btnSecondLinePanel ////////////////
            {
                btnSecondLinePanel.setLayout(new GridBagLayout());
                ((GridBagLayout) btnSecondLinePanel.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).rowHeights = new int[]{0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).rowWeights = new double[]{0.0};

                //-+-+-+-+-+-+-+-+-+-+ autoSearchBox -+-+-+-+-+-+-+-+-+-+
                autoSearchBox = new JCheckBox("PreSearch", true);
                //-+-+-+-+-+-+-+-+-+-+ 待开发 -+-+-+-+-+-+-+-+-+-+
                tableHeadCert = new JCheckBox("Cert", false);
                tableHeadHeader = new JCheckBox("Header", false);
                //-+-+-+-+-+-+-+-+-+-+ pageLabel -+-+-+-+-+-+-+-+-+-+
                pageLabel.setText("0");
                //-+-+-+-+-+-+-+-+-+-+ tableSizeLabel -+-+-+-+-+-+-+-+-+-+
                tableSizeLabel.setText("Table Size:");
                //-+-+-+-+-+-+-+-+-+-+ tablePageSizeField -+-+-+-+-+-+-+-+-+-+
                tablePageSizeField.setColumns(3);
                tablePageSizeField.setText("" + TABLE_ONEPAGE_SIZE);
                //-+-+-+-+-+-+-+-+-+-+ prePageBtn -+-+-+-+-+-+-+-+-+-+
                prePageBtn.setText("<---");
                //-+-+-+-+-+-+-+-+-+-+ nextPageBtn -+-+-+-+-+-+-+-+-+-+
                nextPageBtn.setText("--->");

                // pagePanel用于将翻页按钮撑到最右边
                btnSecondLinePanel.add(tableHeadCert, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadHeader, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(pagePanel, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
                btnSecondLinePanel.add(tableSizeLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tablePageSizeField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(sep1, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(autoSearchBox, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(prePageBtn, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(pageLabel, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(nextPageBtn, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 5, 0));
                btnPanel.add(btnSecondLinePanel, BorderLayout.CENTER);
            }
            add(btnPanel, BorderLayout.NORTH);
        }
        //////////////// tabelPanel ////////////////
        {
            //-+-+-+-+-+-+-+-+-+-+ resultTablePanel -+-+-+-+-+-+-+-+-+-+
            tablePanel.setLayout(new GridBagLayout());
            ((GridBagLayout) tablePanel.getLayout()).columnWidths = new int[]{0};
            ((GridBagLayout) tablePanel.getLayout()).rowHeights = new int[]{0, 0};
            ((GridBagLayout) tablePanel.getLayout()).columnWeights = new double[]{1.0};
            ((GridBagLayout) tablePanel.getLayout()).rowWeights = new double[]{1.0, 1E-4};
            tablePanel.add(resultTable.getTable(), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(tablePanel, BorderLayout.CENTER);
        }
    }

    /**
     * @param original 原字符串
     * @param find     需要查找的字符串
     * @return count 返回在原字符串中需要查找的字符串出现的次数
     */
    public static int queryStringOccurrenceNumber(String original, String find) {
        int count = 0;
        while (original.contains(find)) {
            original = original.substring(original.indexOf(find) + find.length());
            count++;
        }
        return count;
    }

    private void initListener() {

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TABLE_PAGE.set(1);
                // 清除数据
                fofaResult.clear();
                latch = new CountDownLatch(1);
                fofaLineTableModel.search(1, searchField.getText().trim());
                showTable(1);
            }
        });
        prePageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                if (TABLE_PAGE.get() > 1)
                    showTable(TABLE_PAGE.decrementAndGet());
            }
        });
        nextPageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                if (AUTO_SEARCH)
                    fofaLineTableModel.autoSearch(FOFA_PAGE.incrementAndGet(), searchField.getText().trim());
                if (TABLE_PAGE.get() == totalTablePage) {
                    latch = new CountDownLatch(1);
                    fofaLineTableModel.search(FOFA_PAGE.incrementAndGet(), searchField.getText().trim());
                }
                showTable(TABLE_PAGE.incrementAndGet());
            }
        });
        exportBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                stdout.println("Export");
            }
        });

        searchField.addActionListener(e -> searchBtn.doClick());


        tablePageSizeField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int t = Integer.parseInt(tablePageSizeField.getText().trim());
                if (t != 0) {
                    TABLE_ONEPAGE_SIZE = t;
                    TABLE_PAGE.set(1);
                    showTable(TABLE_PAGE.get());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                int t = Integer.parseInt(tablePageSizeField.getText().trim());
                if (t != 0) {
                    TABLE_ONEPAGE_SIZE = t;
                    TABLE_PAGE.set(1);
                    showTable(TABLE_PAGE.get());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 在JTextField该方法不生效
            }
        });

        autoSearchBox.addChangeListener(e -> {
            if (autoSearchBox.isSelected())
                AUTO_SEARCH = Boolean.TRUE;
            else
                AUTO_SEARCH = Boolean.FALSE;
        });


        tableHeadHeader.addChangeListener(e -> {
            if (tableHeadHeader.isSelected())
                HEADER = Boolean.TRUE;
            else
                HEADER = Boolean.FALSE;
            FofaLineEntry.addTableHeaderList("header");
        });
        tableHeadCert.addChangeListener(e -> {
            if (tableHeadCert.isSelected())
                CERT = Boolean.TRUE;
            else
                CERT = Boolean.FALSE;
            FofaLineEntry.addTableHeaderList("cert");
        });


        tableHeadCert.addChangeListener(e -> {
            CERT = tableHeadHeader.isSelected();
        });

        tableHeadHeader.addChangeListener(e -> {
            HEADER = tableHeadCert.isSelected();
        });


    }

    private void setNewLine() {
        for (int i = 0; i < fofaLineTableModel.getRowCount(); i++) {
            resultTable.setRowHeight(i, 22 * (1 + Math.max(queryStringOccurrenceNumber(fofaLineTableModel.getValueAt(i, 10).toString(), "<br>"), queryStringOccurrenceNumber(fofaLineTableModel.getValueAt(i, 9).toString(), "<br>"))));
        }

    }

    public void showTable(int page) {
        new Thread(() -> {
            pageLabel.setText("searching...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace(stderr);
            }
            // 设置页码
            totalTablePage = fofaResult.size() % TABLE_ONEPAGE_SIZE == 0 && fofaResult.size() != 0 ? fofaResult.size() / TABLE_ONEPAGE_SIZE : fofaResult.size() / TABLE_ONEPAGE_SIZE + 1;
            pageLabel.setText(page + " / " + totalTablePage);
            if(page > totalTablePage)// 避免当前页超过总页数的逻辑错误
                TABLE_PAGE.getAndDecrement();
            totalSizeLabel.setText("Total Size: " + FOFA_TOTAL_SIZE);
            pageLabel.setText(TABLE_PAGE + " / " + totalTablePage);

            // 填充Table数据
            int start = (page - 1) * TABLE_ONEPAGE_SIZE;
            int end = (page - 1) * TABLE_ONEPAGE_SIZE + TABLE_ONEPAGE_SIZE;
            if (end <= fofaResult.size()) {
                /**
                 * 这里有一个坑,
                 * 如果直接把fofaResult.subList(start, end)当参数给setLineEntries(),
                 * 会在fofaResult插入新数据后造成界面卡死,甚至Burp卡死,
                 * 具体原因不太了解，整了一个中间量避免出错。
                 */
                List<FofaLineEntry> shown = new ArrayList<>(fofaResult.subList(start, end));
                fofaLineTableModel.setLineEntries(shown);// [ , )
                fofaLineTableModel.fireTableRowsInserted(0, end - start - 1);// [ , ]
            } else if (start < fofaResult.size()) {
                List<FofaLineEntry> shown = new ArrayList<>(fofaResult.subList(start, fofaResult.size()));
                fofaLineTableModel.setLineEntries(shown);
                fofaLineTableModel.fireTableRowsInserted(0, fofaResult.size() - start - 1);// [ , ]
            }
            setNewLine();// 更新table后也要设置行高，否则显示不全

        }).start();
    }
}
