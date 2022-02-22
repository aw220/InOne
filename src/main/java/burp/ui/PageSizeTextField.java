/*
 * PageSizeTextField.java
 * @author: aw220
 * @date: 2022/2/23 上午2:12
 *
 *
 */

package burp.ui;

import burp.ui.model.FofaLineTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static burp.Utils.page;
import static burp.Utils.tableSize;

public class PageSizeTextField extends JTextField implements DocumentListener {

    public PageSizeTextField() {
        getDocument().addDocumentListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        tableSize = Integer.valueOf(this.getText().trim());
        page.set(1);

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
