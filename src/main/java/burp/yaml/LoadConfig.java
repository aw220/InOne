package burp.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static burp.Utils.SettingPath;
import static burp.Utils.stderr;

public class LoadConfig {
    private static final Yaml yaml = new Yaml();

    public LoadConfig() {
    }

    public Map<String, String> getFofaAccount() {
        try {
            InputStream inorder = new FileInputStream(SettingPath);
            return yaml.load(inorder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 设置fofa账户
    public void setFofaAccount(Map<String, String> map) {
        try {
            Writer ws = new OutputStreamWriter(new FileOutputStream(SettingPath), StandardCharsets.UTF_8);
            yaml.dump(map, ws);
        } catch (Exception ex) {
            stderr.println("Save ERROR：" + ex);
            ex.printStackTrace();
        }
    }

}