package burp.ui;

import burp.Utils;
import burp.action.Commons;
import burp.action.RunNucleiAction;
import burp.ui.entry.FioraLineEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static burp.Utils.loadConn;
import static burp.Utils.stderr;

public class FiroraLineEntryMenu extends JPopupMenu {


	private static final long serialVersionUID = 1L;
	private static FioraLineTable fioraLineTable;

	public static void main(String[] args) throws Exception {
		Commons.browserOpen("http://www.7k7k.com", loadConn.getBrowser());
	}

	public static String getValue(int rowIndex,int columnIndex) {
		//由于所有的返回值都是String类型的，都可以直接强制类型转换
		Object value = fioraLineTable.getModel().getValueAt(rowIndex, columnIndex);
		return (String)value;

	}

	FiroraLineEntryMenu(final FioraLineTable fioraLineTable, final int[] rows, final int columnIndex){
		this.fioraLineTable = fioraLineTable;

		JMenuItem itemNumber = new JMenuItem(new AbstractAction(rows.length+" Items Selected") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
			}
		});

		//one line
		JMenuItem editPoCItem = new JMenuItem(new AbstractAction("Edit With VSCode(Double Click PoCFile)") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (rows.length >=50) {
					return;
				}
				FioraLineEntry selecteEntry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
				String path = selecteEntry.getPocFileFullPath();
				Commons.editWithVSCode(path);
			}
		});

		//one line
		JMenuItem showInExplorerItem = new JMenuItem(new AbstractAction("Show In Folder") {
			@Override		
			public void actionPerformed(ActionEvent e) {
				try {
					//JOptionPane.showMessageDialog(null,"Not found editor(code.exe idle.bat) in environment.");
					if (rows != null && rows.length >=0){
						FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
						String path = entry.getPocFileFullPath();
						String dir = new File(path).getParent();
						Commons.OpenFolder(dir);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		//multiple line
		JMenuItem copyFilePathItem = new JMenuItem(new AbstractAction("Copy File Path") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (rows.length >=50) {
					return;
				}
				List<String> paths = new ArrayList<String>();
				for (int row:rows) {
					FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(row);
					String path = entry.getPocFileFullPath();
					paths.add(path);
				}
				String textUrls = String.join(System.lineSeparator(), paths);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(textUrls);
				clipboard.setContents(selection, null);

			}
		});

		
		/**
		 * nuclei -u 127.0.0.1 -t CVE-2020-3580.yaml
		 */
		JMenuItem genSinglePoCCmd = new JMenuItem(new AbstractAction("Generate Command Of This PoC") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
				String path = entry.getPocFileFullPath();
				List<String> targets = Commons.getLinesFromTextArea(FioraPanel.getTitleTable().getTextAreaTarget());
				
				String Command;
				if (entry.isWorkflow()) {
					Command = RunNucleiAction.genWorkflowCommand(targets, path);
				}else {
					Command = RunNucleiAction.genCommand(targets, path);
				}
				Commons.writeToClipboard(Command.trim());
				
			}
		});

		/**
		 * nuclei -u 127.0.0.1 -t CVE-2020-3580.yaml
		 */
		JMenuItem runSinglePoC = new JMenuItem(new AbstractAction("Run This PoC") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
				String path = entry.getPocFileFullPath();
				List<String> targets = Commons.getLinesFromTextArea(FioraPanel.getTitleTable().getTextAreaTarget());
				String Command;
				if (entry.isWorkflow()) {
					Command = RunNucleiAction.genWorkflowCommand(targets, path);
				}else {
					Command = RunNucleiAction.genCommand(targets, path);
				}
				
				RunNucleiAction.run(Command);
			}
		});
		
		/**
		 * 
		 * nuclei -u 127.0.0.1 -tags cisco
		 */
		JMenuItem genCmdWithTags = new JMenuItem(new AbstractAction("Generate Command With Tags") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
				String tags = entry.getTags();
				tags = getTags(tags);
				List<String> targets = Commons.getLinesFromTextArea(FioraPanel.getTitleTable().getTextAreaTarget());
				String Command = RunNucleiAction.genTagsCommand(targets, tags);
				Commons.writeToClipboard(Command.trim());
			}
			
			public String getTags(String tags) {
				String resulttags = JOptionPane.showInputDialog("tags to use", tags).trim();
				return resulttags;
			}
		});
		
		/**
		 * 
		 * nuclei -u 127.0.0.1 -tags cisco
		 */
		JMenuItem runMultipluePoC = new JMenuItem(new AbstractAction("Run PoCs With Tags") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				FioraLineEntry entry = fioraLineTable.getModel().getLineEntries().getValueAtIndex(rows[0]);
				String tags = entry.getTags();
				tags = getTags(tags);
				List<String> targets = Commons.getLinesFromTextArea(FioraPanel.getTitleTable().getTextAreaTarget());
				String Command = RunNucleiAction.genTagsCommand(targets, tags);
				
				RunNucleiAction.run(Command);
			}
			
			public String getTags(String tags) {
				String resulttags = JOptionPane.showInputDialog("tags to use", tags).trim();
				return resulttags;
			}
		});

		//multiple line
		JMenuItem googleSearchItem = new JMenuItem(new AbstractAction("Seach On Google") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (rows.length >=50) {
					return;
				}
				for (int row:rows) {
					//String searchContent = (String)lineTable.getModel().getValueAt(row, columnIndex);
					String searchContent = FiroraLineEntryMenu.getValue(row, columnIndex);
					try {
						searchContent = URLEncoder.encode(searchContent, StandardCharsets.UTF_8.toString());
						String url= "https://www.google.com/search?q="+searchContent;
						Commons.browserOpen(url, loadConn.getBrowser());
					} catch (Exception e) {
						e.printStackTrace(stderr);
					}
				}
			}
		});

		//multiple line
		JMenuItem SearchOnGithubItem = new JMenuItem(new AbstractAction("Seach On Github") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (rows.length >=50) {
					return;
				}
				for (int row:rows) {
					String searchContent = FiroraLineEntryMenu.getValue(row, columnIndex);
					try {
						searchContent = URLEncoder.encode(searchContent, StandardCharsets.UTF_8.toString());
						String url= "https://github.com/search?q=%22"+searchContent+"%22&type=Code";
						Commons.browserOpen(url, loadConn.getBrowser());
					} catch (Exception e) {
						e.printStackTrace(stderr);
					}
				}
			}
		});

		//multiple line
		JMenuItem SearchOnFoFaItem = new JMenuItem(new AbstractAction("Seach On FoFa") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				if (rows.length >=50) {
					return;
				}
				for (int row:rows) {
					String searchContent = FiroraLineEntryMenu.getValue(row, columnIndex);
					try {
						searchContent = URLEncoder.encode(searchContent, StandardCharsets.UTF_8.toString());
						String url= "https://fofa.so/result?q=%22"+searchContent+"%22";
						Commons.browserOpen(url, loadConn.getBrowser());
					} catch (Exception e) {
						e.printStackTrace(stderr);
					}
				}
			}

		});

		//one line
		JMenuItem SearchOnFioraItem = new JMenuItem(new AbstractAction("Seach On Fiora") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String searchContent = FiroraLineEntryMenu.getValue(rows[0], columnIndex);
				FioraPanel.getTextFieldSearch().setText(searchContent);
			}
		});



		this.add(itemNumber);
		this.addSeparator();

		this.add(editPoCItem);//file operate
		this.add(showInExplorerItem);
		this.add(copyFilePathItem);

		this.addSeparator();//run check
		this.add(genSinglePoCCmd);
		this.add(genCmdWithTags);
		this.add(runSinglePoC);
		this.add(runMultipluePoC);

		this.addSeparator();// search
		this.add(googleSearchItem);
		this.add(SearchOnGithubItem);
		this.add(SearchOnFoFaItem);
		this.add(SearchOnFioraItem);// 1 line
	}
}
