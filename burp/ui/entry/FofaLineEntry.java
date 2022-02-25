/*
 * LineEntry.java
 * @author: aw220
 * @date: 2022/2/21 下午2:45
 *
 *
 */

package burp.ui.entry;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FofaLineEntry {
    private static final Logger log = LogManager.getLogger(FofaLineEntry.class);

    //{"#", "ip", "host", "port", "server", "type", "country_name", "protocol","header","cert"};
    private String ip = ""; // 资产IP
    private String host = ""; // 资产host
    private String port = "";// 资产port
    private String server = "";// 资产网页title
    private String type = "";// 资产type
    private String country_name = "";// 资产country_name
    private String protocol = "";// 资产protocol
    private String header = "";// 资产rsp包header
    private String cert = "";// 资产cert
    private static LinkedHashMap<String, Integer> preferredWidths = new LinkedHashMap<String, Integer>();

    public static LinkedHashMap<String, Integer> fetchTableHeaderAndWidth() {
        if (preferredWidths.isEmpty()) {
            preferredWidths.put("#", 3);
            preferredWidths.put("ip", 15);
            preferredWidths.put("host", 25);
            preferredWidths.put("port", 6);
            preferredWidths.put("server", 15);
            preferredWidths.put("type", 12);
            preferredWidths.put("country_name", 16);
            preferredWidths.put("protocol", 8);
            preferredWidths.put("cert", 40);
            preferredWidths.put("header", 400);// 给宽一点，免得显示不全，手动拉宽比较麻烦
//			preferredWidths.put("title", 40);莫名其妙不能用title，不知道哪里出错了
        }
        return preferredWidths;
    }

    public static List<String> fetchTableHeaderList() {
        LinkedHashMap<String, Integer> headers = fetchTableHeaderAndWidth();
        List<String> keys = new ArrayList<>(headers.keySet());
        return keys;
    }

    public static void addTableHeaderList(String header) {
        preferredWidths.put(header, 30);
    }

    public static void subTableHeaderList(String header) {
        preferredWidths.remove(header);
    }

    public static Logger getLog() {
        return log;
    }

    public FofaLineEntry() {
        if (preferredWidths.isEmpty()) {
            preferredWidths.put("#", 5);
            preferredWidths.put("ip", 10);
            preferredWidths.put("host", 25);
            preferredWidths.put("port", 10);
            preferredWidths.put("title", 40);
            preferredWidths.put("country_name", 15);
            preferredWidths.put("server", 15);
            preferredWidths.put("protocol", 10);
            preferredWidths.put("type", 20);
        }
    }

    public String getIp() {
        return ip;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }


    public String getCountry_name() {
        return country_name;
    }

    public String getServer() {
        return server;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getType() {
        return type;
    }

    public String getHeader() {
        return header;
    }

    public String getCert() {
        return cert;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String ToJson() {//注意函数名称，如果是get set开头，会被认为是Getter和Setter函数，会在序列化过程中被调用。
        return new Gson().toJson(this);
    }

    public static FofaLineEntry FromJson(String json) {//注意函数名称，如果是get set开头，会被认为是Getter和Setter函数，会在序列化过程中被调用。
        return new Gson().fromJson(json, FofaLineEntry.class);
    }

    public static List<String> fetchFieldNames() {
        Field[] fields = FofaLineEntry.class.getDeclaredFields();
        List<String> result = new ArrayList<String>();
        for (Field field : fields) {
            result.add(field.getName());
        }
        return result;
    }

    @Deprecated
    public Object callGetter(String paraName) throws Exception {
        Method[] methods = FofaLineEntry.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("get" + paraName)) {
                Class<?> returnType = method.getReturnType();
                Object result = method.invoke(this);
                return returnType.cast(result);
            }
        }
        return "";
    }

    public Object fetchValue(String paraName) throws Exception {
        //Field[] fields = LineEntry.class.getDeclaredFields();
        Field field = FofaLineEntry.class.getDeclaredField(paraName);
        return field.get(this);
    }

    public static void main(String args[]) {
        System.out.println(fetchTableHeaderList());
    }
}
