package org.hystrix.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据接收端,模拟服务提供者
 * @author Tango
 * @date 20171123
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class RemoteApp  {
    
    private static final String SLEEP = "sleep";
    
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @PostMapping("/p")
    String post(@RequestParam String value) {
        String sleepTimeString = redisTemplate.opsForValue().get(SLEEP);
        if (sleepTimeString != null) {
            try {
                int sleepTime = Integer.parseInt(sleepTimeString);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("recive value:" + value);
        return "success: " + value;
    }
    
    public static void main( String[] args ) {
        SpringApplication.run(RemoteApp.class, args);
    }
}
