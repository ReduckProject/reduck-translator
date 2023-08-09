package net.reduck.translator;


import com.gogh.plugin.entity.ResultEntity;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易有道智云翻译服务api调用demo
 * api接口: https://openapi.youdao.com/api
 */
public class TranslateDemo {

    private static final String APP_KEY = "0fa3d104f727468b";     // 您的应用ID
    private static final String APP_SECRET = "r3jXdwtd7JpjifLMLllgI05iFFStBe42";  // 您的应用密钥

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams("who are you");
        // 添加鉴权相关参数
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            System.out.println(JsonUtils.jsonFormat(new String(result, StandardCharsets.UTF_8)));
        }
        System.exit(0);
    }

    private final static Gson gson = new Gson();
    public static ResultEntity query(String content) {
        Map<String, String[]> params = createRequestParams(content);
        // 添加鉴权相关参数
        try {
            AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            return gson.fromJson(new String(result), ResultEntity.class);
        }

        return null;
    }
    public static String translate(String content) {
        Map<String, String[]> params = createRequestParams(content);
        // 添加鉴权相关参数
        try {
            AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            System.out.println(JsonUtils.jsonFormat(new String(result, StandardCharsets.UTF_8)));
        }

        return new String(result);
    }

    private static Map<String, String[]> createRequestParams(String q) {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/%E8%87%AA%E7%84%B6%E8%AF%AD%E8%A8%80%E7%BF%BB%E8%AF%91/API%E6%96%87%E6%A1%A3/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
         */
//        String q = "Who are you";
        String from = "auto";
        String to = "zh-CHS";
        String vocabId = "";

        return new HashMap<String, String[]>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
//            put("vocabId", new String[]{vocabId});
        }};
    }
}
