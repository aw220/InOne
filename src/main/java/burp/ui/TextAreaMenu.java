package burp.ui;

import burp.BurpExtender;
import burp.Utils;
import burp.action.Commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import static burp.Utils.globalConfig;
import static burp.Utils.loadConn;

public class TextAreaMenu extends JPopupMenu {

	PrintWriter stdout;
	PrintWriter stderr;
	public TextAreaMenu(final String selectedText){

		try{
			stdout = new PrintWriter(stdout, true);
			stderr = new PrintWriter(stderr, true);
		}catch (Exception e){
			stdout = new PrintWriter(System.out, true);
			stderr = new PrintWriter(System.out, true);
		}

		List<String> selectedItems = Arrays.asList(selectedText.split(System.lineSeparator()));

		JMenuItem OpenUrlItem = new JMenuItem(new AbstractAction("Open URL") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (selectedItems.size() >=50) {
					return;
				}
				for (String item:selectedItems) {
					try {
						String url= new URL(item).toString();
						Commons.browserOpen(url, loadConn.getBrowser());
					} catch (Exception e) {
						e.printStackTrace(stderr);
					}
				}
			}
		});
		this.add(OpenUrlItem);

		JMenuItem googleSearchItem = new JMenuItem(new AbstractAction("Google It") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (selectedItems.size() >=50) {
					return;
				}
				for (String item:selectedItems) {
					String url= "https://www.google.com/search?q=%22"+URLEncoder.encode(item)+"%22";
					try {
						Commons.browserOpen(url, loadConn.getBrowser());
					} catch (Exception e) {
						e.printStackTrace(stderr);
					}
				}
			}
		});

		this.add(googleSearchItem);


		JMenuItem SearchOnGithubItem = new JMenuItem(new AbstractAction("Seach On Github") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (selectedItems.size() >=50) {
					return;
				}
				for (String item:selectedItems) {
					try {
						String url= "https://github.com/search?q=%s&type=Code";
						URI uri = new URI(String.format(url, URLEncoder.encode(item)));
						Desktop desktop = Desktop.getDesktop();
						if(Desktop.isDesktopSupported()&&desktop.isSupported(Desktop.Action.BROWSE)){
							desktop.browse(uri);
						}
					} catch (Exception e2) {
						e2.printStackTrace(stderr);
					}
				}
			}
		});

		this.add(SearchOnGithubItem);
	}
}
