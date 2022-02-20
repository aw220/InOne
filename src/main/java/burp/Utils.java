/*
 * Utils.java
 * @author: aw220
 * @date: 2022/2/20 下午11:19
 *
 *
 */

package burp;



import burp.yaml.LoadConfig;

import javax.swing.event.EventListenerList;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    public static final LoadConfig loadConn = new LoadConfig();
    public static final String EXTENSION_NAME = "InOne";
    public static String FOFA_ID = loadConn.getFofaAccount().get("FofaId");
    public static String FOFA_KEY = loadConn.getFofaAccount().get("FofaKey");
    public static Boolean AUTO_SEARCH = Boolean.TRUE;
    public static IBurpExtenderCallbacks cbs;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public final static String[] HEADER = new String[]{"ip", "host", "port", "title"};
    public final static Integer pageSize = 100;
    public final static Integer tableSize = 20;
    public static final String SettingPath = "InOneSetting.yml";
    public static final String ConfigPath = "InOneConfig.yml";
    public static final EventListenerList eventListenerList = new EventListenerList();
    // 用于给showTable加锁，避免搜索时还没返回数据就渲染。
    public static CountDownLatch latch = new CountDownLatch(0);


    public static List<List<String>> result = new ArrayList<>();
    public static AtomicInteger page = new AtomicInteger(1);
    public static Object searchBoxHelper;

    public static void addActionListener(ActionListener l) {
        eventListenerList.add(ActionListener.class, l);
    }


}
