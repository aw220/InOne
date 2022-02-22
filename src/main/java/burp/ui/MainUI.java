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
import burp.ui.model.FioraLineTableModel;
import burp.ui.model.FofaLineTableModel;

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
    private JTabbedPane tabbedPaneRoot;
    private FofaPanel fofa;
    private ConfigPanel config;
    private FioraPanel fiora;


    public MainUI() {
        initComponents();

    }

    private void initComponents() {
        tabbedPaneRoot = new JTabbedPane();
        fofa = new FofaPanel();
        fiora = new FioraPanel(globalConfig.getPoctRootPath());
        config = new ConfigPanel();
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