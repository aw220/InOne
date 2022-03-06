package burp.yaml;

import burp.ui.entry.CustomLineEntry;
import fofa.FofaClient;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static burp.Utils.*;

public class LoadConfig {
    private static Yaml yaml;
    DumperOptions options = new DumperOptions();

    private static Map<String, Object> cache = new HashMap<>();

    public LoadConfig() {
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);

        File yamlSetting = new File(SETTING_PATH);
        if (!(yamlSetting.exists() && yamlSetting.isFile())) {
            // 初次使用创建配置文件
            initSetting();
        } else {
            readFromDisk();
        }
    }

    public String getBrowser() {
        return (String) cache.get("BrowserPath");
    }

    public void setBrowser(String path) {
        cache.put("BrowserPath", path);
    }

    public void setCustom(List<CustomLineEntry> customLineEntries) {
        cache.put("Custom", customLineEntries);
    }

    public String getFofaId() {
        return (String) ((Map) cache.get("FofaAccount")).get("id");
    }

    public String getFofaKey() {
        return (String) ((Map) cache.get("FofaAccount")).get("key");
    }

    public List<CustomLineEntry> getCustom() {
        return (List<CustomLineEntry>) cache.get("Custom");
    }

    // 设置fofa账户
    public void setFofaAccount(String id, String key) {
        cache.put("FofaAccount", new HashMap() {{
            put("id", id);
            put("key", key);
        }});
    }

    public void saveToDisk() {
        try {
            Writer ws = new OutputStreamWriter(new FileOutputStream(SETTING_PATH), StandardCharsets.UTF_8);
            addListener();
            yaml.dump(cache, ws);
        } catch (FileNotFoundException e) {
            e.printStackTrace(stderr);
        }
        stdout.println("save success!!!");
    }

    private void readFromDisk() {
        try {
            cache = yaml.load(new InputStreamReader(new FileInputStream(SETTING_PATH)));
        } catch (FileNotFoundException e) {
            e.printStackTrace(stderr);
        }
    }

    // 初始化设置信息
    public void initSetting() {
        cache.put("FofaAccount", new HashMap() {{
            put("id", "");
            put("key", "");
        }});
        cache.put("BrowserPath", "");
        cache.put("Custom", new ArrayList<CustomLineEntry>(Collections.singletonList(new CustomLineEntry("", ""))));
        saveToDisk();
    }

    // 用于某些需要及时更新配置的功能
    private void addListener() {
        client = new FofaClient(getFofaId(), getFofaKey());
    }

    public static void main(String[] args) {
        LoadConfig loadConfig = new LoadConfig();


        System.out.println(cache);
        System.out.println(loadConfig.getCustom().get(0).getName());
    }
}