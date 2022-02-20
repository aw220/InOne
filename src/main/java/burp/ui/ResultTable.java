/*
 * ResultTable.java
 * @author: aw220
 * @date: 2022/2/20 下午3:48
 * 自定义Table，用于展示FOFA搜索结果
 *
 */

package burp.ui;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static burp.Utils.*;

public class ResultTable extends JTable {
    FofaMenu fofaMenu = new FofaMenu();
    int column = -1;
    int row = -1;

    public ResultTable(TableModel dm) {
        super(dm);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int focusedColumnIndex = column = ResultTable.this.columnAtPoint(e.getPoint());
                int focusedRowIndex = row = ResultTable.this.rowAtPoint(e.getPoint());
                if (focusedRowIndex == -1 || focusedColumnIndex == -1) return;
                ResultTable.this.setColumnSelectionInterval(focusedColumnIndex, focusedColumnIndex);
                ResultTable.this.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                if (e.getButton() == MouseEvent.BUTTON3) {//鼠标右键

                    stdout.println(focusedColumnIndex + ";" + focusedRowIndex);
                    // 弹出菜单
                    fofaMenu.show(ResultTable.this, e.getX(), e.getY());
                } else if (e.getClickCount() > 1) {
                    try {
                        if ((page.get() - 1) * tableSize + row < result.size())
                            ((JTextField) searchBoxHelper).setText(((JTextField) searchBoxHelper).getText() + " && " + HEADER[column] + "=\"" + result.get((page.get() - 1) * tableSize + row).toArray()[column].toString() + "\"");
                    } catch (Exception ex) {
                        stderr.println("DOUBLE CLICK ERROR：" + ex);
                    }
                }
            }
        });

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private class FofaMenu extends JPopupMenu {
        public FofaMenu() {

            JMenuItem copy = new JMenuItem(new AbstractAction("Copy") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    stdout.println("右键菜单：复制");
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // 得到系统剪贴板
                    String text = result.get(page.get()).toArray()[column].toString();
                    stdout.println(text);
                    StringSelection selection = new StringSelection(text);
                    clipboard.setContents(selection, null);
                }
            });
            this.add(copy);


        }
    }

}
