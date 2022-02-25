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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
        public static final String EXTENSION_NAME = "InOne";
    public static Boolean AUTO_SEARCH = Boolean.TRUE;
    public static Boolean HEADER = Boolean.FALSE;
    public static Boolean CERT = Boolean.FALSE;
    public static IBurpExtenderCallbacks cbs;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public static final LoadConfig loadConn = new LoadConfig();
    public static FofaClient client = new FofaClient(loadConn.getFofaId(), loadConn.getFofaKey());
    public final static Integer pageSize = 20;// 向fofa请求的每次返回的信息条目数量
    public static Integer tableSize = 4;// 展示表格一页大小
    public static Integer totalSize = 0;// fofa服务器返回的被搜索信息总条数
    public static final String SettingPath = "InOneSetting.yml";
    public static final String ConfigPath = "InOneConfig.yml";
    public static final EventListenerList eventListenerList = new EventListenerList();
    public static GlobalConfig globalConfig = new GlobalConfig();
    // 用于给showTable加锁，避免搜索时还没返回数据就渲染。
    public static CountDownLatch latch = new CountDownLatch(0);
    public static AtomicInteger tablePage = new AtomicInteger(1);// table的页数
    public static AtomicInteger fofaPage = new AtomicInteger(1);// fofa搜索的的页数
    public static List<FofaLineEntry> fofaResult = new ArrayList<>();
    public static int totalTablePage = 0;
    public static JTextField searchBoxHelper;
    public static JLabel pageLabelHelper;


}
