/*
 * MainUI.java
 * @author: aw220
 * @date: 2022/2/19 下午3:49
 * 总UI
 *
 */

package burp.ui;

import burp.ITab;
import burp.action.FofaAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static burp.Utils.*;

public class MainUI extends JPanel implements ITab {
    private final FofaAction fofaAction = new FofaAction();
    private JTabbedPane tabbedPaneRoot;
    private JPanel fofa;
    private JPanel config;
    private JPanel pagePanel;
    private ResultTable resultTable;
    private JLabel searchLabel;
    private JLabel pageLabel;
    private JLabel configFofaIdLabel;
    private JLabel configFofaKeyLabel;
    private JButton searchBtn;
    private JButton exportBtn;
    private JButton prePageBtn;
    private JButton nextPageBtn;
    private JButton configSaveBtn;
    private JTextField searchBox;
    private JTextField configFofaIdBox;
    private JTextField configFofaKeyBox;


    public MainUI() {
        initComponents();

    }

    private void initComponents() {
        tabbedPaneRoot = new JTabbedPane();
        fofa = new JPanel();
        config = new JPanel();
        pagePanel = new JPanel();
        searchLabel = new JLabel();
        pageLabel = new JLabel();
        configFofaIdLabel = new JLabel();
        configFofaKeyLabel = new JLabel();
        searchBtn = new JButton();
        exportBtn = new JButton();
        prePageBtn = new JButton();
        nextPageBtn = new JButton();
        configSaveBtn = new JButton();
        searchBox = new JTextField();
        searchBoxHelper = searchBox;
        configFofaIdBox = new JTextField();
        configFofaKeyBox = new JTextField();

        //================ this ================
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{1.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{1.0, 1.0E-4};

        //================ tabbedPaneRoot ================
        {

            //================ FOFA ================
            {
                fofa.setLayout(new GridBagLayout());
                ((GridBagLayout) fofa.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 0};
                ((GridBagLayout) fofa.getLayout()).rowHeights = new int[]{0, 0, 0,0};
                ((GridBagLayout) fofa.getLayout()).columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout) fofa.getLayout()).rowWeights = new double[]{0.0, 0.0,0.0, 1.0E-4};

                //-+-+-+-+-+-+-+-+-+-+ searchLabel -+-+-+-+-+-+-+-+-+-+
                searchLabel.setText("Rule:");
                fofa.add(searchLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ pageLabel -+-+-+-+-+-+-+-+-+-+
                pageLabel.setText("0");
                //-+-+-+-+-+-+-+-+-+-+ searchBox -+-+-+-+-+-+-+-+-+-+
                fofa.add(searchBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ searchBtn -+-+-+-+-+-+-+-+-+-+
                searchBtn.setText("Search");
                fofa.add(searchBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ exportBtn -+-+-+-+-+-+-+-+-+-+
                exportBtn.setText("Export");
                fofa.add(exportBtn, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ prePageBtn -+-+-+-+-+-+-+-+-+-+
                prePageBtn.setText("<---");
                //-+-+-+-+-+-+-+-+-+-+ nextPageBtn -+-+-+-+-+-+-+-+-+-+
                nextPageBtn.setText("--->");
                //-+-+-+-+-+-+-+-+-+-+ pagePanel -+-+-+-+-+-+-+-+-+-+
                pagePanel.add(prePageBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                pagePanel.add(pageLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                pagePanel.add(nextPageBtn, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                fofa.add(pagePanel, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ resultTable -+-+-+-+-+-+-+-+-+-+
                resultTable = new ResultTable(new DefaultTableModel(HEADER, 0));
                JScrollPane scrollPane1 = new JScrollPane();
                scrollPane1.setViewportView(resultTable);
                fofa.add(scrollPane1, new GridBagConstraints(0, 2, 4, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                tabbedPaneRoot.addTab("FOFA", fofa);

            }

            //================ config ================
            {
                config.setLayout(new GridBagLayout());
                ((GridBagLayout) config.getLayout()).columnWidths = new int[]{0, 0, 0};
                ((GridBagLayout) config.getLayout()).rowHeights = new int[]{0, 0, 0, 0};
                ((GridBagLayout) config.getLayout()).columnWeights = new double[]{0.0, 1.0, 1.0E-4};
                ((GridBagLayout) config.getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};
                //-+-+-+-+-+-+-+-+-+-+ configIdLabel -+-+-+-+-+-+-+-+-+-+
                configFofaIdLabel.setText("FOFAid:");
                config.add(configFofaIdLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ configIdBox -+-+-+-+-+-+-+-+-+-+
                config.add(configFofaIdBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ configKeyLabel -+-+-+-+-+-+-+-+-+-+
                configFofaKeyLabel.setText("FOFAkey:");
                config.add(configFofaKeyLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ configKeyBox -+-+-+-+-+-+-+-+-+-+
                config.add(configFofaKeyBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
                //-+-+-+-+-+-+-+-+-+-+ configSaveBtn -+-+-+-+-+-+-+-+-+-+
                configSaveBtn.setText("Save");
                configSaveBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Map<String, String> map = new HashMap<>();
                        map.put("FofaId", configFofaIdBox.getText());
                        map.put("FofaKey", configFofaKeyBox.getText());
                        loadConn.setFofaAccount(map);
                    }
                });
                config.add(configSaveBtn, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
                tabbedPaneRoot.addTab("Config", config);
            }
            add(tabbedPaneRoot, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        }

        //================ initFofa ================
        {
            searchBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    page.set(1);
                    // 清除数据
                    result.clear();
                    latch = new CountDownLatch(1);
                    fofaAction.search(1, searchBox.getText());
                    showTable(1);
                }
            });
            prePageBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (page.get() > 1)
                        showTable(page.decrementAndGet());
                }
            });
            nextPageBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    showTable(page.incrementAndGet());
                    if (AUTO_SEARCH)
                        fofaAction.autoSearch(page.get(), searchBox.getText());
                }
            });
            exportBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stdout.println("Export");
                }
            });
        }
        //================ initConfig ================
        {
            //-+-+-+-+-+-+-+-+-+-+ configSaveBtn -+-+-+-+-+-+-+-+-+-+
            configFofaIdBox.setText(FOFA_ID);
            configFofaKeyBox.setText(FOFA_KEY);
        }


    }


    public void showTable(int page) {
        new Thread(() -> {
            pageLabel.setText("searching...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                stderr.println("showTable latch ERROR:" + e);
            }
            Object[][] tableData = new Object[tableSize][HEADER.length];
            // 填充Table数据
            int i = (page - 1) * tableSize;
            int j = 0;
            try {
                while (i < (page - 1) * tableSize + tableSize && i < result.size()) {
                    tableData[j++] = result.get(i++).toArray();
                }
            } catch (Exception ex) {
                stderr.println("showTable ERROR:" + ex);
            }
            pageLabel.setText(page + "");
            resultTable.setModel(new DefaultTableModel(tableData, HEADER));
            stdout.println("page：" + page);
        }).start();
    }


    @Override
    public String getTabCaption() {
        return EXTENSION_NAME;
    }

    @Override
    public Component getUiComponent() {
        return this;
    }
}