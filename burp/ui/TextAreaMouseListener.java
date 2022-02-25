package burp.ui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextAreaMouseListener extends MouseAdapter{
	JTextArea textArea;
	String selected;

	public TextAreaMouseListener(JTextArea textArea){
		this.textArea = textArea;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JPopupMenu jp;
		this.selected = textArea.getSelectedText();
		if (selected != null && selected !="") {
			jp = new TextAreaMenu(selected);

		}else {
			jp = new JPopupMenu();
			jp.add("^_^");
		}
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			// 弹出菜单
			jp.show(textArea, arg0.getX(), arg0.getY());
		}
	}
}
