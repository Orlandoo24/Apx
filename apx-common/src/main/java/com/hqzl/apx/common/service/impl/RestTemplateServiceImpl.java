package com.hqzl.apx.common.service.impl;

import com.hqzl.apx.common.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class RestTemplateServiceImpl implements RestTemplateService {




    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Object get(String url, Class c, Map query) {
        return restTemplate.getForObject(url, c, query);
    }

    @Override
    public Object post(String url, Class c, Map query) {
        return restTemplate.postForObject(url, query, c);
    }

    @Override
    public Object postHasHeaders(String url, Class c, Map query,Map <String,String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            httpHeaders.add(entry.getKey(),entry.getValue());
        }
        HttpEntity<String> request = new HttpEntity(headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, request, c);
        return exchange.getBody();
    }
}
