/*
 * FofaParser.java
 * @author: aw220
 * @date: 2022/2/21 下午4:24
 *
 *
 */

package burp.action.parser;

import burp.ui.entry.FofaLineEntry;
import com.alibaba.fastjson.JSONArray;
import fofa.SeoSearch;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static burp.Utils.*;

public class FofaParser {

    public static List<FofaLineEntry> fromArray(JSONArray list) throws IOException {
        List<FofaLineEntry> result = new ArrayList<>();
        CountDownLatch latchSeo = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            executorService.schedule(() -> {
                FofaLineEntry t = new FofaLineEntry();
                //{"#", "ip", "host", "port", "server", "type", "country_name", "protocol", "title","header","cert"};
                t.setIp(list.getJSONArray(finalI).get(FIELDS.indexOf("ip")).toString());
                t.setHost(list.getJSONArray(finalI).get(FIELDS.indexOf("host")).toString());
                t.setPort(list.getJSONArray(finalI).get(FIELDS.indexOf("port")).toString());
                if (SERVER) t.setServer(list.getJSONArray(finalI).get(FIELDS.indexOf("server")).toString());
                else t.setServer("");
                if (TYPE) t.setType(list.getJSONArray(finalI).get(FIELDS.indexOf("type")).toString());
                else t.setType("");
                if (COUNTRY) t.setCountry(list.getJSONArray(finalI).get(FIELDS.indexOf("country")).toString());
                else t.setCountry("");
                if (PROTOCOL) t.setProtocol(list.getJSONArray(finalI).get(FIELDS.indexOf("protocol")).toString());
                else t.setProtocol("");
                if (TITLE)
                    t.setTitle(StringEscapeUtils.unescapeHtml4((list.getJSONArray(finalI).get(FIELDS.indexOf("title")).toString())));
                else t.setTitle("");
                if (SEO) {
                    if (!Pattern.compile("(([01]{0,1}\\d{0,1}\\d|2[0-4]\\d|25[0-5])\\.){3}([01]{0,1}\\d{0,1}\\d|2[0-4]\\d|25[0-5])").matcher(list.getJSONArray(finalI).get(FIELDS.indexOf("host")).toString()).find()) {
                        List<String> seo = null;
                        try {
                            seo = new SeoSearch().search(list.getJSONArray(finalI).get(FIELDS.indexOf("host")).toString());
                        } catch (Exception e) {
                            e.printStackTrace(stderr);
                        }
                        t.setBd_rank(seo.get(0));
                        t.setMbd_rank(seo.get(1));
//                    t.set_360_rank(seo.get(2));
//                    t.setSm_rank(seo.get(3));
//                    t.setSg_rank(seo.get(4));
                        t.setGg_rank(seo.get(2));
                    } else {
                        t.setBd_rank("");
                        t.setMbd_rank("");
//                t.set_360_rank("");
//                t.setSm_rank("");
//                t.setSg_rank("");
                        t.setGg_rank("");
                    }
                }

                if (CERT) {
                    // cert只显示十进制的Serial Number
                    Matcher m = Pattern.compile("Version.*?\n.*").matcher("<html>" + ((String) list.getJSONArray(finalI).get(FIELDS.indexOf("cert"))).replace("\n", "<br>") + "</html>");
                    if (m.find()) t.setCert(m.group(0));
                    else {
                        t.setCert("");
                    }
                } else t.setCert("");
                if (HEADER) {
                    t.setHeader(("<html>" + list.getJSONArray(finalI).get(FIELDS.indexOf("header"))).replace("\n", "<br>") + "</html>");
                } else t.setHeader("");
                latchSeo.countDown();
                result.add(t);
            }, 0, TimeUnit.SECONDS);
        }
        try {
            latchSeo.await();
        } catch (
                InterruptedException e) {
            e.printStackTrace(stderr);
        }
        return result;
    }

}
