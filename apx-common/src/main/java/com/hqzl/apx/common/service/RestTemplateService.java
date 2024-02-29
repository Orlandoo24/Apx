package com.hqzl.apx.common.service;



import java.util.Map;

public interface RestTemplateService {
    Object get(String url ,Class c, Map query);

    Object post(String url, Class c, Map query);

    Object postHasHeaders(String url, Class c, Map query,Map <String,String> headers);
}
