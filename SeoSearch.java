/*
 * SeoSearch.java
 * @author: aw220
 * @date: 2022/3/3 上午12:06
 *
 *
 */

package fofa;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static burp.Utils.stderr;
import static burp.Utils.stdout;

public class SeoSearch {
    private final String[] ua = new String[]{"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Safari/605.1.15", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.41 Safari/537.36 Edg/88.0.705.22"};
    private static final String HOST2 = "https://rest.aizhan.com/";
    private static final String HOST1 = "https://www.aizhan.com/cha/";
    private static final String BAIDU = "baidurank/infos?callback=jQuery19108516582853209248_{2}&domain={4}&rn={0}&cc={1}&_={3}";
    private static final String _360_SHENMA = "/pr/so?callback=jQuery19108516582853209248_{2}&domain={4}&rn={0}&cc={1}&_={3}";
    private static final String SOUGOU = "/pr/sogou?callback=jQuery19108516582853209248_{2}&domain={4}&rn={0}&cc={1}&_={3}";

    public SeoSearch() {
    }

    public List<String> search(String tar) {
        String target = tar.trim().replace("https://", "").replace("http://", "");
        Document doc = null;
        try {
            doc = Jsoup.connect(HOST1 + target + "/").userAgent(ua[(new SecureRandom()).nextInt(3)]).get();
        } catch (IOException e) {
            e.printStackTrace(stderr);
            // 失败了，再来一次
            try {
                doc = Jsoup.connect(HOST1 + target + "/").userAgent(ua[(new SecureRandom()).nextInt(3)]).get();
            } catch (IOException e1) {
                e1.printStackTrace(stderr);

            }
        }
        List<String> result = new ArrayList<>();
        result.add(doc.select("#google_pr").select("img").attr("alt"));
        if (doc.select("#baidurank_br").select("img").attr("alt").equals("n")) {
            List<String> list = new ArrayList<>();
            for (Element element : doc.getElementsByTag("script")) {
                if (element.data().contains("token")) {
                    for (String s : element.data().toString().split("var"))
                        if (s.contains("token")) {
                            for (String s1 : s.split(",")) {
                                Matcher m = Pattern.compile("[0-9a-z]*(\")?$").matcher(s1);
                                if (m.find())
                                    //[ rn, cc, token ]
                                    list.add(m.group(0).replace("\"", ""));
                            }
                            break;
                        }
                    break;
                }
            }
            long _2 = System.currentTimeMillis();
            try {
                doc = Jsoup.connect(HOST2 + BAIDU.replace("{0}", list.get(0)).replace("{1}", list.get(1)).replace("{2}", Long.toString(_2)).replace("{3}", _2 + 1 + "").replace("{4}", target)).ignoreContentType(true).userAgent(ua[(new SecureRandom()).nextInt(3)]).get();
            } catch (Exception exception) {
                exception.printStackTrace(stderr);
                try {            // 失败了，再来一次
                    doc = Jsoup.connect(HOST2 + BAIDU.replace("{0}", list.get(0)).replace("{1}", list.get(1)).replace("{2}", Long.toString(_2)).replace("{3}", _2 + 1 + "").replace("{4}", target)).ignoreContentType(true).userAgent(ua[(new SecureRandom()).nextInt(3)]).get();
                } catch (Exception ex) {
                    ex.printStackTrace(stderr);
                }
            }
            Matcher matcher = Pattern.compile("\\{.*}").matcher(doc.body().toString());
            if (matcher.find()) {// 百度
                JSONObject json = JSONObject.parseObject(matcher.group(0));
//                stdout.println(json);
                result.add(String.valueOf(json.get("br")));
                result.add(String.valueOf(json.get("m_br")));
            }
        } else {
            result.add(doc.select("#baidurank_br").select("img").attr("alt"));
            result.add(doc.select("#baidurank_mbr").select("img").attr("alt"));
//            result.add(doc.select("#360_pr").select("img").attr("alt"));
//            result.add(doc.select("#sm_pr").select("img").attr("alt"));
//            result.add(doc.select("#sogou_pr").select("img").attr("alt"));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

    }
}
