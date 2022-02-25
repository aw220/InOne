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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static burp.Utils.*;
import static burp.ui.FofaPanel.queryStringOccurrenceNumber;

public class FofaParser {

    public static List<FofaLineEntry> fromArray(JSONArray list) {
        List<FofaLineEntry> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FofaLineEntry t = new FofaLineEntry();
            //{"#", "ip", "host", "port", "server", "type", "country_name", "protocol","header","cert"};
            t.setIp(list.getJSONArray(i).get(0).toString());
            t.setHost(list.getJSONArray(i).get(1).toString());
            t.setPort(list.getJSONArray(i).get(2).toString());
            t.setServer(list.getJSONArray(i).get(3).toString());
            t.setType(list.getJSONArray(i).get(4).toString());
            t.setCountry_name(list.getJSONArray(i).get(5).toString());
            t.setProtocol(list.getJSONArray(i).get(6).toString());
            if (CERT && HEADER) {
                // cert只显示十进制的Serial Number
                Matcher m = Pattern.compile("<html>" + "Version.*?\n.*").matcher((String) ((String) list.getJSONArray(i).get(7)).replace("\n", "<br>") + "</html>");
                if (m.find())
                    t.setCert(m.group(0));
                else {
                    t.setCert("");
                }
                t.setHeader(("<html>" + list.getJSONArray(i).get(8)).replace("\n", "<br>") + "</html>");
            } else if (CERT) {
                // cert只显示十进制的Serial Number
                Matcher m = Pattern.compile("<html>" + "Version.*?\n.*").matcher((String) ((String) list.getJSONArray(i).get(7)).replace("\n", "<br>") + "</html>");
                if (m.find())
                    t.setCert(m.group(0));
                else {
                    t.setCert("");
                }
            } else if (HEADER) {
                t.setHeader(("<html>" + list.getJSONArray(i).get(7)).replace("\n", "<br>") + "</html>");
            }
//            t.setTitle(StringEscapeUtils.unescapeHtml4(list.get(i).get(7)));// 待开发
            result.add(t);
        }
        return result;
    }


    public static void main(String[] args) {
        String str = "Version:  v3\nSerial Number: 317003566541851547875090732476009404434398";
        Matcher m = Pattern.compile("Version.*?\n.*").matcher(str);
        if (m.find())
            System.out.println(m.group(0));
    }
}
