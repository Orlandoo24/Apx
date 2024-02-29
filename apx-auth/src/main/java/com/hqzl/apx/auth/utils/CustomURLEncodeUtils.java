package com.hqzl.apx.auth.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomURLEncodeUtils {
    public static Map<String,String> requestTokenToMap(String requestToken) {
        List<NameValuePair> parameters = URLEncodedUtils.parse(requestToken, StandardCharsets.UTF_8);
        Map<String,String> map= new HashMap();
        for (NameValuePair parameter : parameters) {
            String key = parameter.getName();
            String value = parameter.getValue();
            map.put(key,value);
        }
        return map;
    }
}
