/*
 * TextFieldListener.java
 * @author: aw220
 * @date: 2022/2/25 下午11:45
 *
 *
 */

package burp.ui.listener;

import burp.ui.panel.OptionsPanel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 保存各个路径设置参数，自动保存的listener
 */

public class TextFieldListener implements DocumentListener {
	boolean listenerIsOn = true;
	@Override
	public void removeUpdate(DocumentEvent e) {
		if (listenerIsOn) {
			OptionsPanel.saveToConfigFromGUI();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (listenerIsOn) {
			OptionsPanel.saveToConfigFromGUI();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		if (listenerIsOn) {
			OptionsPanel.saveToConfigFromGUI();
		}
	}
}