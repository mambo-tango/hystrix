package org.hystrix.local.scheduler;

import java.util.Date;

import org.hystrix.local.service.PostService;
import org.hystrix.local.utils.HelloWorldHystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SpringScheduler {
    
    private Logger LOGGER = LoggerFactory.getLogger(SpringScheduler.class);
    
    @Autowired
    PostService postService;
    
    public static final String URL1 = "http://10.1.237.101:8001/p";
    public static final String URL2 = "http://10.1.237.102:8002/p";
    
    @Scheduled(fixedRate = 1 * 1000)
    public void doJobByFixedRate() {
        try {
            //组名等
            String key1 = "hystrix.command.http";
            String key2 = "hystrix.command.http2";
            for (int i = 0; i < 1; i++) {
                Date date = new Date();
                String result1 = new HelloWorldHystrixCommand(URL1, "xxxx"+i, key1).execute();
//                System.out.println(date+"post value:"+result1);
                String result2 = new HelloWorldHystrixCommand(URL2, "yyyy"+i, key2).execute();
//                System.out.println(date+"post value:"+result2);
                LOGGER.info(date+"post value:"+result1);
                LOGGER.error(date+"post value:"+result2);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
