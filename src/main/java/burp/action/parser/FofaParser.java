/*
 * FofaParser.java
 * @author: aw220
 * @date: 2022/2/21 下午4:24
 *
 *
 */

package burp.action.parser;

import burp.ui.entry.FofaLineEntry;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class FofaParser {

    public static List<FofaLineEntry> fromArray(List<List<String>> list) {
        List<FofaLineEntry> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FofaLineEntry t = new FofaLineEntry();
            //{"#", "ip", "host", "port", "title"};
            t.setIp(list.get(i).get(0));
            t.setHost(list.get(i).get(1));
            t.setPort(list.get(i).get(2));
            t.setServer(list.get(i).get(3));
            t.setType(list.get(i).get(4));
            t.setCountry_name(list.get(i).get(5));
            t.setProtocol(list.get(i).get(6));
            t.setTitle(StringEscapeUtils.unescapeHtml4(list.get(i).get(7)));
            result.add(t);
        }
        return result;
    }

}
