/*
 * PageSizeTextField.java
 * @author: aw220
 * @date: 2022/2/25 下午11:45
 *
 *
 */

/*
 * PageSizeTextField.java
 * @author: aw220
 * @date: 2022/2/23 上午2:12
 *
 *
 */

package burp.ui.panel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static burp.Utils.TABLE_PAGE;
import static burp.Utils.TABLE_ONEPAGE_SIZE;

public class PageSizeTextField extends JTextField implements DocumentListener {

    public PageSizeTextField() {
        getDocument().addDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        TABLE_ONEPAGE_SIZE = Integer.valueOf(this.getText().trim());
        TABLE_PAGE.set(1);

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
