/*
 * ConfigPanel.java
 * @author: aw220
 * @date: 2022/3/4 下午8:43
 *
 *
 */

/*
 * ConfigPanel.java
 * @author: aw220
 * @date: 2022/2/21 下午5:59
 *
 *
 */

package burp.ui.panel;

import burp.ui.model.CustomTableModel;
import burp.ui.table.CustomTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static burp.Utils.*;

public class ConfigPanel extends JPanel {
    private JLabel configFofaIdLabel;
    private JLabel configFofaKeyLabel;
    private JLabel configBrowserLabel;
    private JLabel customLabel;
    private JLabel customAddOrSub;
    private JPanel customPanel;
    private JButton configSaveBtn;
    private JTextField configFofaIdBox;
    private JTextField configFofaKeyBox;
    private JTextField configBrowserPathBox;
    private JTextField customName;
    private JTextField customPath;
    private CustomTableModel customTableModel;

    public ConfigPanel() {
        init();
    }

    private void init() {
        configFofaIdLabel = new JLabel();
        configFofaKeyLabel = new JLabel();
        configBrowserLabel = new JLabel();
        customLabel = new JLabel();
        customAddOrSub = new JLabel();
        customPanel = new JPanel();
        configSaveBtn = new JButton();
        configFofaIdBox = new JTextField();
        configFofaKeyBox = new JTextField();
        configBrowserPathBox = new JTextField();
        customName = new JTextField();
        customPath = new JTextField();
        customTableModel = new CustomTableModel();

        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 1.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        //-+-+-+-+-+-+-+-+-+-+ configIdLabel -+-+-+-+-+-+-+-+-+-+
        configFofaIdLabel.setText("FOFAid:");
        add(configFofaIdLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ configIdBox -+-+-+-+-+-+-+-+-+-+
        add(configFofaIdBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ configKeyLabel -+-+-+-+-+-+-+-+-+-+
        configFofaKeyLabel.setText("FOFAkey:");
        add(configFofaKeyLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ configKeyBox -+-+-+-+-+-+-+-+-+-+
        add(configFofaKeyBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ borwserLabel -+-+-+-+-+-+-+-+-+-+
        configBrowserLabel.setText("Browser Path:");
        add(configBrowserLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ configKeyBox -+-+-+-+-+-+-+-+-+-+
        add(configBrowserPathBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));
        //-+-+-+-+-+-+-+-+-+-+ custom -+-+-+-+-+-+-+-+-+-+
        customPanel.setLayout(new GridBagLayout());
        ((GridBagLayout) customPanel.getLayout()).columnWidths = new int[]{0, 0, 0};
        ((GridBagLayout) customPanel.getLayout()).rowHeights = new int[]{0};
        ((GridBagLayout) customPanel.getLayout()).columnWeights = new double[]{0.1, 0.9, 0};
        ((GridBagLayout) customPanel.getLayout()).rowWeights = new double[]{0.0};
        customLabel.setText("自定义send to:");
        customAddOrSub.setText("+");
        add(customLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(10, 5, 5, 5), 0, 0));

        add(new CustomTable(customTableModel).getTable(), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 5, 5), 0, 0));


        //-+-+-+-+-+-+-+-+-+-+ configSaveBtn -+-+-+-+-+-+-+-+-+-+
        configSaveBtn.setText("Save");
        configSaveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                loadConn.setFofaAccount(configFofaIdBox.getText(), configFofaKeyBox.getText());
                loadConn.setBrowser(configBrowserPathBox.getText());
                loadConn.setCustom(customTableModel.getLineEntries());
                loadConn.saveToDisk();
            }
        });
        add(configSaveBtn, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));

        configFofaIdBox.setText(loadConn.getFofaId());
        configFofaKeyBox.setText(loadConn.getFofaKey());
        configBrowserPathBox.setText(loadConn.getBrowser());
        customTableModel.addLineEntries(loadConn.getCustom());

    }
}
