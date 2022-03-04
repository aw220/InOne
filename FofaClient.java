//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fofa;

import burp.Utils;
import burp.ui.model.FofaLineTableModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.gv7.woodpecker.requests.RawResponse;
import me.gv7.woodpecker.requests.Requests;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import static burp.Utils.*;

public class FofaClient {
    public final String host = "https://fofa.info";
    public final String path = "/api/v1/search/all";
    public static final String TIP_API = "https://api.fofa.info/v1/search/tip?q=";
    private String email;
    private String key;
    private ObjectMapper mapper = new ObjectMapper();
    private final String[] ua = new String[]{
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Safari/605.1.15",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:65.0) Gecko/20100101 Firefox/65.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.41 Safari/537.36 Edg/88.0.705.22"
    };


    public FofaClient(String email, String key) {
        this.email = email;
        this.key = key;
    }

    public HashMap<String, String> getData(String query, int page, int connectTimeout, int socksTimeout) {
        RawResponse response;
        HashMap<String, String> result = new HashMap<>();
        stdout.println(getFofaSearchApi(query, page));
        try {
            response = Requests.get(getFofaSearchApi(query, page))
                    .headers(new HashMap<String, String>() {{
                        put("User-Agent", ua[(new SecureRandom()).nextInt(3)]);
                    }})
                    .connectTimeout(connectTimeout)
                    .socksTimeout(socksTimeout)
                    .send();
        } catch (Exception e) {
            e.printStackTrace(stderr);
            result.put("code", "error");
            result.put("msg", e.getMessage());
            return result;
        }
        if (response != null) {
            int code = response.statusCode();
            result.put("code", String.valueOf(code));
            try {
                if (code == 200) {
                    String body = response.readToText(); // 默认使用utf-8编码
                    result.put("msg", body);
                } else if (code == 401) {
                    result.put("msg", "请求错误状态码401，可能是没有在config中配置有效的email和key，或者您的账号权限不足无法使用api进行查询。");
                } else if (code == 502) {
                    result.put("msg", "请求错误状态码502，可能是账号限制了每次请求的最大数量，建议尝试修改config中的maxSize为100");
                } else {
                    result.put("msg", "请求响应错误,状态码" + code);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace(stderr);
                result.put("code", "error");
                result.put("msg", e.getMessage());
                return result;
            }
        }
        result.put("code", "error");
        return result;
    }

    public String getFofaSearchApi(String query, int page) {
        return host + path + "?email=" + email + "&key=" + key + "&page=" +
                page + "&size=" + FOFA_ONEPAGE_SIZE + "&fields=" + getFields() + "&qbase64=" + Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));
    }


    public String getFields() {
        StringBuilder builder = new StringBuilder();
        for (String i : FofaLineTableModel.getTitletList().subList(1, 4)) {
            builder.append(i).append(",");
        }
        if (SERVER)
            builder.append("server").append(",");
        if (TYPE)
            builder.append("type").append(",");
        if (COUNTRY)
            builder.append("country").append(",");
        if (PROTOCOL)
            builder.append("protocol").append(",");
        if (TITLE)
            builder.append("title").append(",");
        if (CERT)
            builder.append("cert").append(",");
        if (HEADER)
            builder.append("header").append(",");
        FIELDS = Arrays.asList(builder.toString().split(","));
        String a = builder.toString();
        stdout.println(a);
        return a.substring(0, a.length() - 1);
    }

}
