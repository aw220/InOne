/*
 * ConfigPanel.java
 * @author: aw220
 * @date: 2022/2/21 下午5:59
 *
 *
 */

package burp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static burp.Utils.*;

public class ConfigPanel extends JPanel {
    private JLabel configFofaIdLabel;
    private JLabel configFofaKeyLabel;
    private JLabel configBrowserLabel;
    private JButton configSaveBtn;
    private JTextField configFofaIdBox;
    private JTextField configFofaKeyBox;
    private JTextField configBrowserPathBox;

    public ConfigPanel() {
        init();
    }

    private void init() {
        configFofaIdLabel = new JLabel();
        configFofaKeyLabel = new JLabel();
        configBrowserLabel = new JLabel();
        configSaveBtn = new JButton();
        configFofaIdBox = new JTextField();
        configFofaKeyBox = new JTextField();
        configBrowserPathBox = new JTextField();

        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWidths = new int[]{0, 0, 0};
        ((GridBagLayout) getLayout()).rowHeights = new int[]{0, 0, 0, 0, 0};
        ((GridBagLayout) getLayout()).columnWeights = new double[]{0.0, 1.0, 1.0E-4};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0E-4};
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
        //-+-+-+-+-+-+-+-+-+-+ configSaveBtn -+-+-+-+-+-+-+-+-+-+
        configSaveBtn.setText("Save");
        configSaveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                loadConn.setFofaAccount(configFofaIdBox.getText(), configFofaKeyBox.getText());
                loadConn.setBrowser(configBrowserPathBox.getText());
                loadConn.saveToDisk();
            }
        });
        configFofaIdBox.setText(loadConn.getFofaId());
        configFofaKeyBox.setText(loadConn.getFofaKey());
        configBrowserPathBox.setText(loadConn.getBrowser());
        add(configSaveBtn, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 0, 5, 5), 0, 0));
    }
}
