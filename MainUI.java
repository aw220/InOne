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
    private FofaPanel seo;
    private FioraPanel fiora;
    private JPanel draw;
    private ConfigPanel config;


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