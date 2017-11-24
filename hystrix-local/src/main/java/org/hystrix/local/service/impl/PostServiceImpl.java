package org.hystrix.local.service.impl;

import java.util.List;

import org.apache.http.NameValuePair;
import org.hystrix.local.service.PostService;
import org.hystrix.local.utils.HttpClientUtil;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public String postDate(String url, List<NameValuePair> urlParameters) {
        return HttpClientUtil.post(url, urlParameters);
    }


}
