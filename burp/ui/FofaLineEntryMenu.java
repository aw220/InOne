package burp.ui;

import burp.action.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.net.URL;

import static burp.Utils.*;

public class FofaLineEntryMenu extends JPopupMenu {


    private static final long serialVersionUID = 1L;
    private static FofaLineTable fofaLineTable;

    public static void main(String[] args) throws Exception {
        Commons.browserOpen("[]", loadConn.getBrowser());
    }

    public static String getValue(int rowIndex, int columnIndex) {
        //由于所有的返回值都是String类型的，都可以直接强制类型转换
        Object value = fofaLineTable.getModel().getValueAt(rowIndex, columnIndex);
        return (String) value;

    }

    public FofaLineEntryMenu(final FofaLineTable fofaLineTable, final int[] rows, final int columnIndex) {
        FofaLineEntryMenu.fofaLineTable = fofaLineTable;

        this.add(new JMenuItem(new AbstractAction(rows.length + " Items Selected") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        }));
        this.addSeparator();

        this.add(new JMenuItem(new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // 得到系统剪贴板
                StringBuilder text = new StringBuilder();
                for (int row : rows)
                    if (row == rows.length - 1) {
                        try {
                            text.append(FofaLineEntryMenu.getValue(row, columnIndex));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            text.append(FofaLineEntryMenu.getValue(row, columnIndex)).append("\n");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                StringSelection selection;
                selection = new StringSelection(text.toString().replace("\n", ""));
                clipboard.setContents(selection, null);
            }
        }));

        this.add(new JMenuItem(new AbstractAction("Send to Fiora") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        }));

        if (columnIndex == 2) {
            this.add(new JMenuItem(new AbstractAction("Open in Browser") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    //{"#", "ip", "host", "port", "title"};
                    for (int row : rows) {
                        try {
                            String url = FofaLineEntryMenu.getValue(row, columnIndex);
                            if (url.contains("http"))
                                url = new URL(url).toString();
                            else
                                url = "http://" + url;
                            Commons.browserOpen(url, loadConn.getBrowser());
                        } catch (Exception e) {
                            e.printStackTrace(stderr);
                        }
                    }
                }
            }));

        }


    }
}
