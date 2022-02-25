/*
 * MainUI.java
 * @author: aw220
 * @date: 2022/2/19 下午3:49
 * 总UI
 *
 */

package burp.ui;

import burp.ITab;
import burp.ui.panel.FioraPanel;
import burp.ui.panel.FofaPanel;

import javax.swing.*;
import java.awt.*;

import static burp.Utils.EXTENSION_NAME;
import static burp.Utils.globalConfig;

public class MainUI extends JPanel implements ITab {
    private JTabbedPane tabbedPaneRoot;
    private FofaPanel fofa;
    private ConfigPanel config;
    private FioraPanel fiora;
    private JPanel draw;


    public MainUI() {
        initComponents();

    }

    private void initComponents() {
        tabbedPaneRoot = new JTabbedPane();
        fofa = new FofaPanel();
        fiora = new FioraPanel(globalConfig.getPoctRootPath());
        config = new ConfigPanel();
        draw = new JPanel();
        //================ this ================
        setLayout(new BorderLayout());
//        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0};
//        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0};
//        ((GridBagLayout) getLayout()).columnWeights = new double[]{1.0, 1.0E-4};
//        ((GridBagLayout) getLayout()).rowWeights = new double[]{1.0, 1.0E-4};

        //================ tabbedPaneRoot ================
        {
            //================ FOFA ================
            tabbedPaneRoot.addTab("FOFA", fofa);
            //================ Fiora ================
            tabbedPaneRoot.addTab("Fiora", fiora);
            //================ draw ================
            tabbedPaneRoot.addTab("Draw", draw);
            //================ config ================
            tabbedPaneRoot.addTab("Config", config);

            add(tabbedPaneRoot);
        }

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