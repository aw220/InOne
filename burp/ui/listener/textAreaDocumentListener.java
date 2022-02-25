package burp.ui.listener;

import burp.GlobalConfig;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class textAreaDocumentListener implements DocumentListener {

	private JTextArea textArea;
	private GlobalConfig globalConfig;

	public textAreaDocumentListener(JTextArea textArea,GlobalConfig config){
		this.textArea = textArea;
		this.globalConfig = config;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		globalConfig.setTargets(textArea.getText());
		globalConfig.saveToDisk();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		globalConfig.setTargets(textArea.getText());
		globalConfig.saveToDisk();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		globalConfig.setTargets(textArea.getText());
		globalConfig.saveToDisk();
	}
}