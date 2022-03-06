/*
 * CustomLineEntry.java
 * @author: aw220
 * @date: 2022/3/4 下午10:25
 *
 *
 */

package burp.ui.entry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CustomLineEntry {

    private String name;
    private String template;
    private static LinkedHashMap<String, Integer> preferredWidths = new LinkedHashMap<String, Integer>();

    public CustomLineEntry() {
    }

    public CustomLineEntry(String name, String template) {
        this.name = name;
        this.template = template;
    }

    public static LinkedHashMap<String, Integer> fetchTableHeaderAndWidth(){
        preferredWidths.put("#", 4);
        preferredWidths.put("name", 15);
        preferredWidths.put("template", 100);
        return preferredWidths;
    }

    public static List<String> fetchTableHeaderList() {
        LinkedHashMap<String, Integer> headers = fetchTableHeaderAndWidth();
        List<String> keys = new ArrayList<>(headers.keySet());
        return keys;
    }

    public Object fetchValue(String paraName) throws Exception {
        //Field[] fields = LineEntry.class.getDeclaredFields();
        Field field = CustomLineEntry.class.getDeclaredField(paraName);
        return field.get(this);
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemplate(String template) {
        this.template = template;
    }



    public static void main(String args[]) {
        System.out.println(fetchTableHeaderList());
    }

}
