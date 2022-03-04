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
import java.util.concurrent.atomic.AtomicInteger;

import static burp.Utils.*;

public class FofaPanel extends JPanel {
    private JPanel btnPanel;
    private JPanel tablePanel;
    private JPanel btnFirstLinePanel;
    private JPanel btnSecondLinePanel;
    private JPanel blankPanel;
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
    private JCheckBox tableHeadServer;
    private JCheckBox tableHeadType;
    private JCheckBox tableHeadCountry;
    private JCheckBox tableHeadProtocol;
    private JCheckBox tableHeadTitle;
    private JCheckBox tableHeadHeader;
    private JCheckBox tableHeadSeo;
    private JSeparator sep1;

    public FofaPanel() {
        init();
        initListener();
    }

    private void init() {

        blankPanel = new JPanel();
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
        tableHeadServer = new JCheckBox("Server", true);
        tableHeadType = new JCheckBox("Type", true);
        tableHeadCountry = new JCheckBox("Country", true);
        tableHeadProtocol = new JCheckBox("Protocol", true);
        tableHeadTitle = new JCheckBox("Title", true);
        tableHeadCert = new JCheckBox("Cert", false);
        tableHeadHeader = new JCheckBox("Header", false);
        tableHeadSeo = new JCheckBox("Seo", false);
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
                ((GridBagLayout) btnSecondLinePanel.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).rowHeights = new int[]{0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
                ((GridBagLayout) btnSecondLinePanel.getLayout()).rowWeights = new double[]{0.0};

                //-+-+-+-+-+-+-+-+-+-+ autoSearchBox -+-+-+-+-+-+-+-+-+-+
                autoSearchBox = new JCheckBox("PreSearch", true);
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

                int gridx = 0;
                // blankPanel用于将翻页按钮撑到最右边
                btnSecondLinePanel.add(tableHeadServer, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadType, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadCountry, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadProtocol, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadTitle, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadCert, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadHeader, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadSeo, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(blankPanel, new GridBagConstraints(gridx++, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
                btnSecondLinePanel.add(tableSizeLabel, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tablePageSizeField, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(sep1, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(autoSearchBox, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(prePageBtn, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(pageLabel, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(nextPageBtn, new GridBagConstraints(gridx++, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 5, 0));
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
            AUTO_SEARCH = ((JCheckBox) e.getSource()).isSelected();
        });

        tableHeadServer.addChangeListener(e -> {
            SERVER = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadType.addChangeListener(e -> {
            TYPE = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadCountry.addChangeListener(e -> {
            COUNTRY = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadProtocol.addChangeListener(e -> {
            PROTOCOL = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadTitle.addChangeListener(e -> {
            TITLE = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadCert.addChangeListener(e -> {
            CERT = ((JCheckBox) e.getSource()).isSelected();
        });

        tableHeadHeader.addChangeListener(e -> {
            HEADER = ((JCheckBox) e.getSource()).isSelected();
        });
        tableHeadSeo.addChangeListener(e -> {
            SEO = ((JCheckBox) e.getSource()).isSelected();
        });


    }

    private void setNewLine() {
        for (int i = 0; i < fofaLineTableModel.getRowCount(); i++) {
            resultTable.setRowHeight(i, 22 * (1 + Math.max(queryStringOccurrenceNumber(fofaLineTableModel.getValueAt(i, FofaLineEntry.fetchTableHeaderList().size() - 1).toString(), "<br>"), queryStringOccurrenceNumber(fofaLineTableModel.getValueAt(i, FofaLineEntry.fetchTableHeaderList().size() - 2).toString(), "<br>"))));
        }

    }

    public void showTable(final int page) {
        new Thread(() -> {
            int page1 = page;
            pageLabel.setText("searching...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace(stderr);
            }
            // 设置页码
            totalTablePage = fofaResult.size() % TABLE_ONEPAGE_SIZE == 0 && fofaResult.size() != 0 ? fofaResult.size() / TABLE_ONEPAGE_SIZE : fofaResult.size() / TABLE_ONEPAGE_SIZE + 1;
            pageLabel.setText(page1 + " / " + totalTablePage);
            if (page1 > totalTablePage) {// 避免当前页超过总页数的逻辑错误
                page1 = TABLE_PAGE.decrementAndGet();
            }
            totalSizeLabel.setText("Total Size: " + FOFA_TOTAL_SIZE);
            pageLabel.setText(TABLE_PAGE + " / " + totalTablePage);

            // 填充Table数据
            int start = (page1 - 1) * TABLE_ONEPAGE_SIZE;
            int end = (page1 - 1) * TABLE_ONEPAGE_SIZE + TABLE_ONEPAGE_SIZE;
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
