/*
 * FofaLineTable.java
 * @author: aw220
 * @date: 2022/2/25 下午11:45
 *
 *
 */

package burp.ui.table;

import burp.ui.entry.FofaLineEntry;
import burp.ui.menu.FofaLineEntryMenu;
import burp.ui.model.FofaLineTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Map;

import static burp.Utils.*;

public class FofaLineTable extends JTable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private FofaLineTableModel fofaLineTableModel;
    private TableRowSorter<FofaLineTableModel> rowSorter;//TableRowSorter vs. RowSorter

    private JScrollPane tableJpanel;

    public JScrollPane getTable() {
        return tableJpanel;
    }

    //将选中的行（图形界面的行）转换为Model中的行数（数据队列中的index）.因为图形界面排序等操作会导致图像和数据队列的index不是线性对应的。
    public int[] SelectedRowsToModelRows(int[] SelectedRows) {

        int[] rows = SelectedRows;
        for (int i = 0; i < rows.length; i++) {
            rows[i] = convertRowIndexToModel(rows[i]);//转换为Model的索引，否则排序后索引不对应〿
        }
        Arrays.sort(rows);//升序

        return rows;
    }

    public FofaLineTable(FofaLineTableModel fofaLineTableModel) {
        //super(lineTableModel);//这个方法创建的表没有header

        this.fofaLineTableModel = fofaLineTableModel;
        this.setFillsViewportHeight(true);//在table的空白区域显示右键菜单
        //https://stackoverflow.com/questions/8903040/right-click-mouselistener-on-whole-jtable-component
        this.setModel(fofaLineTableModel);

        tableinit();
        //FitTableColumns(this);
        addClickSort();
        registerListeners();

        tableJpanel = table();
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend) {
        // show the log entry for the selected row
        FofaLineEntry Entry = this.fofaLineTableModel.getLineEntries().get(row);

        this.fofaLineTableModel.setCurrentlyDisplayedItem(Entry);
        super.changeSelection(row, col, toggle, extend);
    }

    @Override
    public FofaLineTableModel getModel() {
        //return (LineTableModel) super.getModel();
        return fofaLineTableModel;
    }


    public JScrollPane table() {
        JScrollPane scrollPaneRequests = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //允许横向滚动条
        //scrollPaneRequests.setViewportView(titleTable);//titleTable should lay here.
        scrollPaneRequests.setViewportView(this);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//配合横向滚动条
        return scrollPaneRequests;
    }

    public void tableinit() {
        //Font f = new Font("Arial", Font.PLAIN, 12);
        Font f = this.getFont();
        FontMetrics fm = this.getFontMetrics(f);
        int width = fm.stringWidth("A");//一个字符的宽度

        Map<String, Integer> preferredWidths = FofaLineEntry.fetchTableHeaderAndWidth();

        for (String header : FofaLineTableModel.getTitletList()) {
            try {//避免动态删除表字段时，出错
                int multiNumber = preferredWidths.get(header);
                this.getColumnModel().getColumn(this.getColumnModel().getColumnIndex(header)).setPreferredWidth(width * multiNumber);
            } catch (Exception e) {
                e.printStackTrace(stderr);
            }
        }
        //this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//配合横向滚动条

    }

    public void addClickSort() {//双击header头进行排序

        rowSorter = new TableRowSorter<>(fofaLineTableModel);//排序和搜索
        FofaLineTable.this.setRowSorter(rowSorter);

        JTableHeader header = this.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    FofaLineTable.this.getRowSorter().getSortKeys().get(0).getColumn();
                    ////当Jtable中无数据时，jtable.getRowSorter()是nul
                } catch (Exception e1) {
                    e1.printStackTrace(stderr);
                }
            }
        });
    }

    public void registerListeners() {
        FofaLineTable.this.setRowSelectionAllowed(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = ((FofaLineTable) e.getSource()).columnAtPoint(e.getPoint());
                int[] rows = SelectedRowsToModelRows(getSelectedRows());
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {
                    if (!searchBoxHelper.getText().isEmpty()) {
                        try {
                            searchBoxHelper.setText(searchBoxHelper.getText() + " && " + fofaLineTableModel.getColumnName(column) + "=\"" + fofaLineTableModel.getLineEntries().get(rows[0]).fetchValue(getColumnName(column)).toString().replace("\n","") + "\"");
                        } catch (Exception ex) {
                           ex.printStackTrace(stderr);
                        }
                    } else {
                        try {
                            searchBoxHelper.setText(fofaLineTableModel.getColumnName(column) + "=\"" + fofaLineTableModel.getLineEntries().get(rows[0]).fetchValue(getColumnName(column)).toString().replace("\n","") + "\"");
                        } catch (Exception ex) {
                            ex.printStackTrace(stderr);
                        }
                    }
                }
            }

            @Override//title表格中的鼠标右键菜单
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                        int[] rows = getSelectedRows();
                        int col = ((FofaLineTable) e.getSource()).columnAtPoint(e.getPoint()); // 获得列位置
                        if (rows.length == 1) {// 单行被选中，重新获取鼠标点击处为被选中行
                            int row = ((FofaLineTable) e.getSource()).rowAtPoint(e.getPoint());
                            int column = ((FofaLineTable) e.getSource()).columnAtPoint(e.getPoint());
                            ((FofaLineTable) e.getSource()).setColumnSelectionInterval(column, column);
                            ((FofaLineTable) e.getSource()).setRowSelectionInterval(row, row);
                        } else if (rows.length > 1) {
                            int row = ((FofaLineTable) e.getSource()).rowAtPoint(e.getPoint());
                            for (int j : rows) {
                                if (row == j) {
                                    row = -1;
                                    break;
                                }
                            }
                            if (row != -1) {
                                row = ((FofaLineTable) e.getSource()).rowAtPoint(e.getPoint());
                                int column = ((FofaLineTable) e.getSource()).columnAtPoint(e.getPoint());
                                ((FofaLineTable) e.getSource()).setColumnSelectionInterval(column, column);
                                ((FofaLineTable) e.getSource()).setRowSelectionInterval(row, row);
                            }
                        } else {
                            int row = ((FofaLineTable) e.getSource()).rowAtPoint(e.getPoint());
                            int column = ((FofaLineTable) e.getSource()).columnAtPoint(e.getPoint());
                            ((FofaLineTable) e.getSource()).setColumnSelectionInterval(column, column);
                            ((FofaLineTable) e.getSource()).setRowSelectionInterval(row, row);
                        }
                        rows = SelectedRowsToModelRows(getSelectedRows());
                        new FofaLineEntryMenu(FofaLineTable.this, rows, col).show(e.getComponent(), e.getX(), e.getY());

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseReleased(e);
            }
        });
    }
}
