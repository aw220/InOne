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

    //{"#", "ip", "host", "port", "server", "type", "country_name", "protocol","header","cert"};
    private String ip = ""; // 资产IP
    private String host = ""; // 资产host
    private String port = "";// 资产port
    private String server = "";// 资产网页title
    private String type = "";// 资产type
    private String country = "";// 资产country_name
    private String protocol = "";// 资产protocol
    private String title = "";// 资产protocol
    private String bd_rank = "";//
    private String mbd_rank = "";//
//    private String _360_rank = "";//
//    private String sm_rank = "";//
//    private String sg_rank = "";//
    private String gg_rank = "";//
    private String cert = "";// 资产cert
    private String header = "";// 资产rsp包header
    private static LinkedHashMap<String, Integer> preferredWidths = new LinkedHashMap<String, Integer>();

    public static LinkedHashMap<String, Integer> fetchTableHeaderAndWidth() {
        if (preferredWidths.isEmpty()) {
            preferredWidths.put("#", 3);
            preferredWidths.put("ip", 15);
            preferredWidths.put("host", 25);
            preferredWidths.put("port", 6);
            preferredWidths.put("server", 15);
            preferredWidths.put("type", 12);
            preferredWidths.put("country", 8);
            preferredWidths.put("protocol", 8);
            preferredWidths.put("title", 40);
            preferredWidths.put("百度", 6);
            preferredWidths.put("手机百度", 12);
//            preferredWidths.put("360", 6);
//            preferredWidths.put("神马", 6);
//            preferredWidths.put("搜狗", 6);
            preferredWidths.put("谷歌", 6);
            preferredWidths.put("cert", 40);
            preferredWidths.put("header", 400);// 给宽一点，免得显示不全，手动拉宽比较麻烦
        }
        return preferredWidths;
    }

    public static List<String> fetchTableHeaderList() {
        LinkedHashMap<String, Integer> headers = fetchTableHeaderAndWidth();
        List<String> keys = new ArrayList<>(headers.keySet());
        return keys;
    }

    public static void subTableHeaderList(String header) {
        preferredWidths.remove(header);
    }

    public FofaLineEntry() {

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


    public String getCountry() {
        return country;
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

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }

    public String getCert() {
        return cert;
    }

    public String getBd_rank() {
        return bd_rank;
    }

    public String getMbd_rank() {
        return mbd_rank;
    }

//    public String get_360_rank() {
//        return _360_rank;
//    }
//
//    public String getSm_rank() {
//        return sm_rank;
//    }
//
//    public String getSg_rank() {
//        return sg_rank;
//    }

    public String getGg_rank() {
        return gg_rank;
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

    public void setCountry(String country) {
        this.country = country;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBd_rank(String bd_rank) {
        this.bd_rank = bd_rank;
    }

    public void setMbd_rank(String mbd_rank) {
        this.mbd_rank = mbd_rank;
    }

//    public void set_360_rank(String _360_rank) {
//        this._360_rank = _360_rank;
//    }
//
//    public void setSm_rank(String sm_rank) {
//        this.sm_rank = sm_rank;
//    }
//
//    public void setSg_rank(String sg_rank) {
//        this.sg_rank = sg_rank;
//    }

    public void setGg_rank(String gg_rank) {
        this.gg_rank = gg_rank;
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
        switch (paraName) {
            case "百度":
                paraName = "bd_rank";
                break;
            case "手机百度":
                paraName = "mbd_rank";
                break;
            case "360":
                paraName = "_360_rank";
                break;
            case "神马":
                paraName = "sm_rank";
                break;
            case "搜狗":
                paraName = "sg_rank";
                break;
            case "谷歌":
                paraName = "gg_rank";
                break;
            default:
                break;
        }
        Field field = FofaLineEntry.class.getDeclaredField(paraName);
        return field.get(this);
    }

    public static void main(String args[]) {
        System.out.println(fetchTableHeaderList());
    }
}
