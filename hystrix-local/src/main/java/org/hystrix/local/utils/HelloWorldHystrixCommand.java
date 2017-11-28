package org.hystrix.local.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class HelloWorldHystrixCommand extends HystrixCommand<String> {

    private String url;
    private String value;
    
    public HelloWorldHystrixCommand(String url, String value, String key) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(key))//针对相同的接口一般CommandKey值相同，目的是把HystrixCommand，HystrixCircuitBreaker，HytrixCommandMerics
                                                                            //以及其他相关对象关联在一起，形成一个原子组。采用原生接口的话，默认值为类名；采用注解形式的话，默认值为方法名。
                        .andCommandKey(HystrixCommandKey.Factory.asKey(key))//对CommandKey分组，用于真正的隔离。相同CommandGroupKey会使用同一个线程池或者信号量。一般情况相同业务功能会使用相同的CommandGroupKey。
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(key))//如果说CommandGroupKey只是逻辑隔离，那么ThreadPoolKey就是物理隔离，当没有设置ThreadPoolKey的时候，
                                                                                   //线程池或者信号量的划分按照CommandGroupKey，当设置了ThreadPoolKey，那么线程池和信号量的划分就按照ThreadPoolKey来处理，
                                                                                    //相同ThreadPoolKey采用同一个线程池或者信号量。
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withCircuitBreakerErrorThresholdPercentage(20) //（出错百分比阈值，当达到此阈值后，开始短路。默认50%）
                                        .withCircuitBreakerSleepWindowInMilliseconds(8000)  //（断路多久以后开始尝试是否恢复，默认5s）
                                        .withCircuitBreakerRequestVolumeThreshold(3)        //// 在统计数据之前，必须在10秒内发出3个请求。  默认是20
                                        .withFallbackEnabled(true)
                                        .withCircuitBreakerForceClosed(false)//是否强制关闭熔断开关，如果强制关闭了熔断开关，则请求不会被降级，一些特殊场景可以动态配置该开关，默认为false。
                                        .withExecutionIsolationThreadInterruptOnTimeout(true).withExecutionTimeoutInMilliseconds(200)));//配置超时时间 
        this.url = url;
        this.value = value;
        
    }

    @Override
    protected String run() throws Exception {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("value", value));
        return HttpClientUtil.post(url, urlParameters);
    }
    
    @Override
    protected String getFallback() {
        return "url:"+url + " ->熔断";
    }

}

