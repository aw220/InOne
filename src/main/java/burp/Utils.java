/*
 * Utils.java
 * @author: aw220
 * @date: 2022/2/20 下午11:19
 *
 *
 */

package burp;


import burp.ui.entry.FofaLineEntry;
import burp.yaml.LoadConfig;
import fofa.FofaClient;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    public static final LoadConfig loadConn = new LoadConfig();
    public static FofaClient client = new FofaClient(loadConn.getFofaId(), loadConn.getFofaKey());
    public static final String EXTENSION_NAME = "InOne";
    public static Boolean AUTO_SEARCH = Boolean.FALSE;
    public static IBurpExtenderCallbacks cbs;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public final static Integer pageSize = 1000;
    public static Integer tableSize = 4;
    public static final String SettingPath = "InOneSetting.yml";
    public static final String ConfigPath = "InOneConfig.yml";
    public static final EventListenerList eventListenerList = new EventListenerList();
    public static GlobalConfig globalConfig = new GlobalConfig();
    // 用于给showTable加锁，避免搜索时还没返回数据就渲染。
    public static CountDownLatch latch = new CountDownLatch(0);
    public static AtomicInteger page = new AtomicInteger(1);
    public static List<FofaLineEntry> fofaResult = new ArrayList<>();
    public static int totalTablePage = 0;
    public static JTextField searchBoxHelper;
    public static JLabel pageLabelHelper;
}
