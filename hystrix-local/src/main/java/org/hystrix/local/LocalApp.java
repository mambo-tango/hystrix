package org.hystrix.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 调用方，模拟服务消费者
 * @author Tango
 * @date 20171123
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class LocalApp  {
    
    public static void main( String[] args ) {
        SpringApplication.run(LocalApp.class, args);
    }
}
