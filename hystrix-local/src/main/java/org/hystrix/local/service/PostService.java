package org.hystrix.local.service;

import java.util.List;

import org.apache.http.NameValuePair;

public interface PostService {

    /**
     * @author Tango
     * @date 20171123
     * @param url
     * @param urlParameters
     * @return
     */
    String postDate(String url, List<NameValuePair> urlParameters);
}
