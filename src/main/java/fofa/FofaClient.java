//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fofa;

import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r4v3zn.fofa.core.DO.FofaData;
import com.r4v3zn.fofa.core.DO.User;
import com.r4v3zn.fofa.core.DO.UserLogin;
import fofa.FofaClientConsts;
import fofa.FofaFieldsConsts;
import com.r4v3zn.fofa.core.enmus.UserVipLevelEnum;
import com.r4v3zn.fofa.core.exception.FofaException;
import com.r4v3zn.fofa.core.util.HttpUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static burp.Utils.*;

public class FofaClient {
    private String email;
    private String key;
    private ObjectMapper mapper = new ObjectMapper();

    public FofaClient(String email, String key) {
        this.email = email;
        this.key = key;
    }

    public UserLogin getUserLogin() {
        return new UserLogin(this.email, this.key);
    }

    public User getUser() throws Exception {
        String loginEmail = this.email;
        String loginKey = this.key;
        String url = FofaClientConsts.BASE_URL + FofaClientConsts.GET_USER_INFO_URI;
        Map<String, Object> map = new HashMap();
        map.put("email", loginEmail);
        map.put("key", loginKey);
        String rsp = HttpUtils.doGet(url, map);
        JsonNode node = this.mapper.readTree(rsp);
        JsonNode errorNode = node.get("error");
        if (errorNode != null && errorNode.asBoolean()) {
            throw new FofaException(node.get("errmsg").asText());
        } else {
            String email = node.get("email").asText();
            String userName = node.get("username").asText();
            Integer fCoin = node.get("fcoin").asInt();
            Boolean isVip = node.get("isvip").asBoolean();
            Integer vipLevel = node.get("vip_level").asInt();
            Boolean isVerified = node.get("is_verified").asBoolean();
            String avatar = node.get("avatar").asText();
            Integer message = node.get("message").asInt();
            String fofacliVersion = node.get("fofacli_ver").asText();
            Boolean fofaServer = node.get("fofacli_ver").asBoolean();
            UserVipLevelEnum vipLevelEnum = vipLevel == 1 ? UserVipLevelEnum.VIP : UserVipLevelEnum.SVIP;
            User user = new User(email, userName, fCoin, isVip, vipLevelEnum, isVerified, avatar, message, fofacliVersion, fofaServer);
            return user;
        }
    }

    public FofaData getData(String q) throws Exception {
        return this.getData(q, 1, 100, "host", false);
    }

    public FofaData getData(String q, Integer page) throws Exception {
        return this.getData(q, page, 100, "host", false);
    }

    public FofaData getData(String q, Integer page, Integer size) throws Exception {
        return this.getData(q, page, size, "host", false);
    }

    public FofaData getData(String q, Integer page, Integer size, String fields) throws Exception {
        return this.getData(q, page, size, fields, false);
    }

    public FofaData getData(String q, Integer page, Integer size, String fields, Boolean full) throws Exception {
        this.checkParam(q, size, fields);
        page = page < 0 ? 1 : page;
        full = full == null ? false : full;
        String url = FofaClientConsts.BASE_URL + FofaClientConsts.SEARCH_URI;
        Map<String, Object> map = new HashMap();
        map.put("qbase64", Base64.encode(q));
        map.put("page", page);
        map.put("size", size);
        map.put("fields", fields);
        map.put("full", full);
        map.put("key", this.key);
        map.put("email", this.email);
        String rsp = HttpUtils.doGet(url, map);
        JsonNode node = this.mapper.readTree(rsp);
        JsonNode errorNode = node.get("error");
        if (errorNode != null && errorNode.asBoolean()) {
            throw new FofaException(node.get("errmsg").asText());
        } else {
            String mode = node.get("mode").asText();
            String query = node.get("query").asText();
            Integer rspPage = node.get("page").asInt();
            Integer totalSize = node.get("size").asInt();
            String results = node.get("results").toString();
            Integer totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
            FofaData fofaData = new FofaData();
            fofaData.setMode(mode);
            fofaData.setPage(rspPage);
            fofaData.setSize(totalSize);
            fofaData.setQuery(query);
            fofaData.setTotalPage(totalPage);
            List<List<String>> list = (List)this.mapper.readValue(results, List.class);
            fofaData.setResults(list);
            return fofaData;
        }
    }

    public void checkParam(String q, Integer size, String fields) throws FofaException {
        if (q != null && !"".equals(q)) {
            if (size > FofaClientConsts.MAX_SIZE) {
                throw new FofaException("max size " + FofaClientConsts.MAX_SIZE);
            } else {
                List<String> splitList = Arrays.asList(fields.split(","));
                splitList = new ArrayList(splitList);
                splitList.removeAll(FofaFieldsConsts.FIELDS_LIST);
                if (splitList.size() > 0) {
                    throw new FofaException(splitList + " not's fields,please delte that");
                }
            }
        } else {
            throw new FofaException("search query cannot be empty");
        }
    }
}
