/*
 * FofaPanel.java
 * @author: aw220
 * @date: 2022/2/21 下午5:53
 *
 *
 */

package burp.ui;

import burp.ui.entry.FofaLineEntry;
import burp.ui.model.FofaLineTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel pageLabel;
    private JLabel tableSizeLabel;
    private JButton searchBtn;
    private JButton exportBtn;
    private JButton prePageBtn;
    private JButton nextPageBtn;
    private JTextField searchField;
    private JTextField tablePageSizeField;
    private JCheckBox autoSearchBox;
    private JCheckBox tableHeadHeader;
    private JCheckBox tableHeadCert;
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
                ((GridBagLayout) btnFirstLinePanel.getLayout()).columnWidths = new int[]{0, 0, 0, 0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).rowHeights = new int[]{0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
                ((GridBagLayout) btnFirstLinePanel.getLayout()).rowWeights = new double[]{0.0};
                //-+-+-+-+-+-+-+-+-+-+ searchLabel -+-+-+-+-+-+-+-+-+-+
                searchLabel.setText("Rule:");
                btnFirstLinePanel.add(searchLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ searchBox -+-+-+-+-+-+-+-+-+-+
                btnFirstLinePanel.add(searchField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ searchBtn -+-+-+-+-+-+-+-+-+-+
                searchBtn.setText("Search");
                btnFirstLinePanel.add(searchBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 0), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ exportBtn -+-+-+-+-+-+-+-+-+-+
                exportBtn.setText("Export");
                btnFirstLinePanel.add(exportBtn, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));

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
                tableHeadHeader = new JCheckBox("Header", false);
                tableHeadCert = new JCheckBox("Cert", false);
                //-+-+-+-+-+-+-+-+-+-+ pageLabel -+-+-+-+-+-+-+-+-+-+
                pageLabel.setText("0");
                //-+-+-+-+-+-+-+-+-+-+ tableSizeLabel -+-+-+-+-+-+-+-+-+-+
                tableSizeLabel.setText("Table Size:");
                //-+-+-+-+-+-+-+-+-+-+ tablePageSizeField -+-+-+-+-+-+-+-+-+-+
                tablePageSizeField.setColumns(3);
                tablePageSizeField.setText("" + tableSize);
                //-+-+-+-+-+-+-+-+-+-+ prePageBtn -+-+-+-+-+-+-+-+-+-+
                prePageBtn.setText("<---");
                //-+-+-+-+-+-+-+-+-+-+ nextPageBtn -+-+-+-+-+-+-+-+-+-+
                nextPageBtn.setText("--->");

                // pagePanel用于将翻页按钮撑到最右边
                btnSecondLinePanel.add(tableHeadHeader, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tableHeadCert, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(pagePanel, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 0, 0));
                btnSecondLinePanel.add(tableSizeLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(tablePageSizeField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
                btnSecondLinePanel.add(sep1, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
//                btnSecondLinePanel.add(autoSearchBox, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(10, 5, 5, 5), 0, 0));
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

    private void initListener() {

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page.set(1);
                // 清除数据
                fofaLineTableModel.clear();
                latch = new CountDownLatch(1);
                fofaLineTableModel.search(1, searchField.getText().trim(),false);
                pageLabel.setText(page + " / " + totalTablePage);
                showTable(1);
            }
        });
        prePageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                if (page.get() > 1)
                    showTable(page.decrementAndGet());
            }
        });
        nextPageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseClicked(e);
                if (AUTO_SEARCH)
                    fofaLineTableModel.autoSearch(page.get(), searchField.getText().trim());
                if (page.get() < totalTablePage)
                    showTable(page.incrementAndGet());
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
                    tableSize = t;
                    page.set(1);
                    showTable(page.get());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        autoSearchBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (autoSearchBox.isSelected())
                    AUTO_SEARCH = Boolean.TRUE;
                else
                    AUTO_SEARCH = Boolean.FALSE;
            }
        });

//        tableHeadHeader.addChangeListener(e -> {
//            if (((JCheckBox) e.getSource()).isSelected())
//                FofaLineEntry.addTableHeaderList("header");
//            else
//                FofaLineEntry.subTableHeaderList("header");
//        });
//
//        tableHeadCert.addChangeListener(e -> {
//            if (((JCheckBox) e.getSource()).isSelected())
//                FofaLineEntry.addTableHeaderList("cert");
//            else
//                FofaLineEntry.subTableHeaderList("cert");
//        });


    }

    public void showTable(int page) {
        new Thread(() -> {
            pageLabel.setText("searching...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace(stderr);
            }
            fofaLineTableModel.clear();
            // 填充Table数据
            int start = (page - 1) * tableSize;
            int end = (page - 1) * tableSize + tableSize;
            totalTablePage = fofaResult.size() / tableSize;
            if (fofaResult.size() % tableSize != 0)
                totalTablePage++;
            pageLabel.setText(page + " / " + totalTablePage);
            if (end <= fofaResult.size()) {
                fofaLineTableModel.setLineEntries(fofaResult.subList(start, end));// [ , )
                fofaLineTableModel.fireTableRowsInserted(0, end - start - 1);// [ , ]
            } else if (start < fofaResult.size()) {
                fofaLineTableModel.setLineEntries(fofaResult.subList(start, fofaResult.size()));
                fofaLineTableModel.fireTableRowsInserted(0, fofaResult.size() - start - 1);// [ , ]
            }
        }).start();

    }
}
