package org.hystrix.local.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;

/**
 * 接口发送.
 * @author Huangxiawu
 * @since 0.0.1
 */
public final class HttpClientUtil {
    
    /**
     * 
     */
    private HttpClientUtil() {}
    
    /**
     * post.
     * @param url url
     * @param urlParameters urlParameters
     * @return str str
     */
    public static String post(final String url, final List<NameValuePair> urlParameters) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
       
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(20000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(20000).build();
        httpPost.setConfig(requestConfig);
        
        httpPost.addHeader("User-Agent", "Mozilla/5.0");
        StringBuffer response = new StringBuffer();
        BufferedReader reader = null;
        try {
            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters, "UTF-8");
            
            httpPost.setEntity(postParams);
            
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            
            
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            
            String inputLine;
            
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            
        } catch (final IOException e) {
            e.printStackTrace();
        } finally{
            try {
                if(reader != null) {
                    reader.close();
                }
                if(httpClient != null) {
                    httpClient.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            
        }
        return response.toString();
    }
}
