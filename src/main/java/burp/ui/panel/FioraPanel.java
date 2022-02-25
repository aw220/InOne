/*
 * FioraPanel.java
 * @author: aw220
 * @date: 2022/2/25 下午11:45
 *
 *
 */

package burp.ui.panel;

import burp.ui.SearchTextField;
import burp.ui.entry.IndexedLinkedHashMap;
import burp.ui.entry.FioraLineEntry;
import burp.action.parser.NucleiParser;
import burp.action.parser.PoctParser;
import burp.action.Commons;
import burp.GlobalConfig;
import burp.ui.model.FioraLineTableModel;
import burp.ui.table.FioraLineTable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;

import static burp.Utils.*;

public class FioraPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private static FioraLineTable titleTable;

	//add table and tablemodel to GUI
	private static FioraLineTableModel titleTableModel = new FioraLineTableModel();
	private IndexedLinkedHashMap<String, FioraLineEntry> BackupLineEntries;

	private static JTextField textFieldSearch;
	public static JRadioButton rdbtnUseRobotInput;
	public static JLabel lblStatus;
	public static JButton buttonFresh;

	public static JTextField getTextFieldSearch() {
		return textFieldSearch;
	}

	/*
	public static void setTextFieldSearch(JTextField textFieldSearch) {
		TitlePanel.textFieldSearch = textFieldSearch;
	}*/

	public static FioraLineTable getTitleTable() {
		return titleTable;
	}

	public static FioraLineTableModel getTitleTableModel() {
		return titleTableModel;
	}

	public IndexedLinkedHashMap<String, FioraLineEntry> getBackupLineEntries() {
		return BackupLineEntries;
	}

	public FioraPanel(String poctRootPath) {//构造函数

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		this.add(createButtonPanel(), BorderLayout.NORTH);



		titleTable = new FioraLineTable(titleTableModel);
		this.add(titleTable.getTableAndDetailSplitPane(),BorderLayout.CENTER);
		//LoadData(MainGUI.poctRootPath+File.separator+"script");
		LoadData(poctRootPath);
	}

	/**
	 * 扫描PoC-T中的poc脚本。D:\github\POC-T\script
	 * @param dir
	 * @return
	 */
	public IndexedLinkedHashMap<String, FioraLineEntry> scanPoCFiles(String dir) {
		IndexedLinkedHashMap<String, FioraLineEntry> lineEntries = new IndexedLinkedHashMap<String, FioraLineEntry>();
		if (null==dir || !new File(dir).exists()){
			return lineEntries;
		}
		Collection<File> files = FileUtils.listFiles(new File(dir), FileFilterUtils.suffixFileFilter(".py"), DirectoryFileFilter.INSTANCE);
		for (File file:files) {
			//System.out.println(file.toString());
			if (file.exists() && file.isFile() && !file.getName().startsWith("__")) {
				FioraLineEntry entry = PoctParser.Parser(file.toString());
				lineEntries.put(file.toString(), entry);
			}
		}
		return lineEntries;
	}
	/**
	 * 默认路径 /Users/bit4woo
	 * @param dir
	 * @return
	 */
	public IndexedLinkedHashMap<String, FioraLineEntry> scanUncleiPoCs(String dir) {
		IndexedLinkedHashMap<String, FioraLineEntry> lineEntries = new IndexedLinkedHashMap<String, FioraLineEntry>();
		if (null==dir || !new File(dir).exists()){
			return lineEntries;
		}
		Collection<File> files = FileUtils.listFiles(new File(dir), FileFilterUtils.suffixFileFilter(".yaml"), DirectoryFileFilter.INSTANCE);
		for (File file:files) {
			//System.out.println(file.toString());
			if (file.exists() && file.isFile()) {
				FioraLineEntry entry = NucleiParser.Parser(file.toString());
				if (entry == null){
					continue;
				}
				lineEntries.put(file.toString(), entry);
			}
		}
		return lineEntries;
	}


	public boolean LoadData(String dir){
		try {//这其中的异常会导致burp退出
			System.out.println("=================================");
			System.out.println("==Start Loading Data From: " + dir+"==");
			stdout.println("==Start Loading Data From: " + dir+"==");

			//showToPoCPanel(scanPoCFiles(dir));
			showToPoCPanel(scanUncleiPoCs(dir));

			System.out.println("==End Loading Data From: "+ dir +"==");//输出到debug console
			stdout.println("==End Loading Data From: "+ dir +"==");
			return true;
		} catch (Exception e) {
			stdout.println("Loading Failed!");
			e.printStackTrace();//输出到debug console
			e.printStackTrace(stderr);
			return false;
		}
	}

	public static void updateTemplate() {
		String pocRoot = globalConfig.getPoctRootPath();
		if (pocRoot.equals(GlobalConfig.defaultPoCRootPath)) {
			try {
				Process process = Runtime.getRuntime().exec("nuclei -ut");
				process.waitFor();//等待执行完成
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			try {
				Process process = Runtime.getRuntime().exec("nuclei -ut -ud "+pocRoot);
				process.waitFor();//等待执行完成
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public JPanel createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton buttonCreate = new JButton("Create PoC");
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File srcFile = new File(globalConfig.getPoctRootPath()+File.separator+
						"cves"+File.separator+"2000"+File.separator+"CVE-2000-0114.yaml");
				File destFile = getInputFile();
				try {
					if (null != destFile) {
						FileUtils.copyFile(srcFile, destFile);
						FioraPanel.buttonFresh.doClick();
						Commons.editWithVSCode(destFile.getAbsolutePath());
					}
				}catch (FileNotFoundException e1) {
					//换个文件再试试
					String content = "aWQ6IENWRS0yMDAwLTAxMTQKCmluZm86CiAgbmFtZTogTWljcm9zb2Z0IEZyb250UGFnZSBFeHRlbnNpb25zIENoZWNrIChzaHRtbC5kbGwpCiAgYXV0aG9yOiByM25haXNzYW5jZQogIHNldmVyaXR5OiBsb3cKICBkZXNjcmlwdGlvbjogRnJvbnRwYWdlIFNlcnZlciBFeHRlbnNpb25zIGFsbG93cyByZW1vdGUgYXR0YWNrZXJzIHRvIGRldGVybWluZSB0aGUgbmFtZSBvZiB0aGUgYW5vbnltb3VzIGFjY291bnQgdmlhIGFuIFJQQyBQT1NUIHJlcXVlc3QgdG8gc2h0bWwuZGxsIGluIHRoZSAvX3Z0aV9iaW4vIHZpcnR1YWwgZGlyZWN0b3J5LgogIHJlZmVyZW5jZToKICAgIC0gaHR0cHM6Ly9udmQubmlzdC5nb3YvdnVsbi9kZXRhaWwvQ1ZFLTIwMDAtMDExNAogICAgLSBodHRwczovL3d3dy5leHBsb2l0LWRiLmNvbS9leHBsb2l0cy8xOTg5NwogIHRhZ3M6IGN2ZSxjdmUyMDAwLGZyb250cGFnZSxtaWNyb3NvZnQKCnJlcXVlc3RzOgogIC0gbWV0aG9kOiBHRVQKICAgIHBhdGg6CiAgICAgIC0gJ3t7QmFzZVVSTH19L192dGlfaW5mLmh0bWwnCgogICAgbWF0Y2hlcnMtY29uZGl0aW9uOiBhbmQKICAgIG1hdGNoZXJzOgogICAgICAtIHR5cGU6IHN0YXR1cwogICAgICAgIHN0YXR1czoKICAgICAgICAgIC0gMjAwCgogICAgICAtIHR5cGU6IHdvcmQKICAgICAgICBwYXJ0OiBib2R5CiAgICAgICAgd29yZHM6CiAgICAgICAgICAtICJfdnRpX2Jpbi9zaHRtbC5kbGwi";
					try {
						if (null != destFile) {
							FileUtils.writeByteArrayToFile(destFile,Base64.getDecoder().decode(content));
							FioraPanel.buttonFresh.doClick();
							Commons.editWithVSCode(destFile.getAbsolutePath());
						}
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public File getInputFile() {
				String message = "New PoC File Name";
				String initialSelectionValue = null;
				while (true) {
					String pocFileName = JOptionPane.showInputDialog(message, initialSelectionValue);
					if (null == pocFileName) {
						return null;
					}
					else if(!pocFileName.trim().equals("")) {
						if (!pocFileName.endsWith(".yaml")) {
							pocFileName = pocFileName+".yaml";
						}

						File destFile = new File(globalConfig.getPoctRootPath()+File.separator+pocFileName);
						if (destFile.exists()) {
							initialSelectionValue = pocFileName;
							continue;
						}else {
							return destFile;
						}
					}
				}
			}
		});
		buttonPanel.add(buttonCreate);

		JButton buttonSearch = new JButton("Search");

		textFieldSearch = new SearchTextField("",buttonSearch);
		buttonPanel.add(textFieldSearch);

		buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = textFieldSearch.getText().trim();
				titleTable.search(keyword);
				lblStatus.setText(titleTableModel.getStatusSummary());
			}
		});
		buttonPanel.add(buttonSearch);

		buttonFresh = new JButton("Fresh");
		buttonPanel.add(buttonFresh);
		buttonFresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTemplate();
				LoadData(globalConfig.getPoctRootPath());
				lblStatus.setText(titleTableModel.getStatusSummary());
				buttonSearch.doClick();
			}
		});

		JButton buttonProxy = new JButton("Proxy");
		buttonPanel.add(buttonProxy);
		buttonProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String originProxy = globalConfig.getProxy();
					String proxy = JOptionPane.showInputDialog("Proxy To Use: eg. http://127.0.0.1:8080", originProxy);
					if (proxy!= null) {
						globalConfig.setProxy(proxy.trim());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton buttonHelp = new JButton("Help");
		buttonPanel.add(buttonHelp);
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Commons.browserOpen("https://nuclei.projectdiscovery.io/nuclei/get-started/#running-nuclei", loadConn.getBrowser());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		rdbtnUseRobotInput = new JRadioButton("RobotInput");
		if (Commons.isMac()) {
			rdbtnUseRobotInput.setSelected(false);
		}else {
			rdbtnUseRobotInput.setSelected(false);
		}

		buttonPanel.add(rdbtnUseRobotInput);

		lblStatus = new JLabel("Status");
		buttonPanel.add(lblStatus);

		return buttonPanel;
	}

	/*
	 * 用于从DB文件中加载数据，没有去重检查。
	 */
	public void showToPoCPanel(IndexedLinkedHashMap<String, FioraLineEntry> lineEntries) {
		//titleTableModel.setLineEntries(new ArrayList<LineEntry>());//clear
		//这里没有fire delete事件，会导致排序号加载文件出错，但是如果fire了又会触发tableModel的删除事件，导致数据库删除。改用clear()
		titleTableModel.clear(false);//clear
		titleTableModel.setListenerIsOn(false);
		int row = lineEntries.size();
		titleTableModel.setLineEntries(lineEntries);//如果listener是on，将触发listener--同步到db文件
		if (row>=1) {
			titleTableModel.fireTableRowsInserted(0, row-1);
		}
		titleTableModel.setListenerIsOn(true);
		System.out.println(row+" title entries loaded from database file");
		stdout.println(row+" title entries loaded from database file");
		FioraPanel.getTitleTable().search("");// hide checked items
		lblStatus.setText(titleTableModel.getStatusSummary());
	}
}
