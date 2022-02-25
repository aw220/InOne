package burp.yaml;

import fofa.FofaClient;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static burp.Utils.*;

public class LoadConfig {
    private static final Yaml yaml = new Yaml();
    private String browser = "";
    private String fofaId = "";
    private String fofaKey = "";
    private static Map<String,String> cache = new HashMap<>();

    public LoadConfig() {
        File yamlSetting = new File(SETTING_PATH);
        if (!(yamlSetting.exists() && yamlSetting.isFile())) {
            // 初次使用创建配置文件
            initSetting();
        }else {
            loadBrowser();
            loadFofaAccount();
        }
    }

    public String getBrowser() {
        return browser;
    }


    private void loadBrowser() {
        try {
            InputStream inorder = new FileInputStream(SETTING_PATH);
            Map<String, String> result = yaml.load(inorder);
            this.browser = result.get("BrowserPath");
        } catch (FileNotFoundException e) {
            e.printStackTrace(stderr);
        }
    }

    public void setBrowser(String path) {
        this.browser = path;
        cache.put("BrowserPath", path);
    }

    public String getFofaId() {
        return fofaId;
    }

    public String getFofaKey() {
        return fofaKey;
    }

    // 设置fofa账户
    public void setFofaAccount(String id, String key) {
        this.fofaId = id;
        this.fofaKey = key;
        cache.put("FofaId", id);
        cache.put("FofaKey", key);
    }

    private void loadFofaAccount() {
        try {
            InputStream inorder = new FileInputStream(SETTING_PATH);
            Map<String, String> result = yaml.load(inorder);
            this.fofaId = result.get("FofaId");
            this.fofaKey = result.get("FofaKey");
            this.browser = result.get("BrowserPath");
        } catch (FileNotFoundException e) {
            e.printStackTrace(stderr);
        }
    }

    public void saveToDisk(){
        try {
            Writer ws = new OutputStreamWriter(new FileOutputStream(SETTING_PATH), StandardCharsets.UTF_8);
            addListener();
            yaml.dump(cache, ws);
        } catch (FileNotFoundException e) {
            e.printStackTrace(stderr);
        }
    }
    // 初始化设置信息
    public void initSetting() {
        Map<String, Object> r = new HashMap<>();
        try {
            Writer ws = new OutputStreamWriter(new FileOutputStream(SETTING_PATH), StandardCharsets.UTF_8);
            yaml.dump(r, ws);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 用于某些需要及时更新配置的功能
    private void addListener(){
        client = new FofaClient(loadConn.getFofaId(), loadConn.getFofaKey());
    }
}