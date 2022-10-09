package com.open.es;

import com.xpc.easyes.autoconfig.annotation.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuxiaowei
 * @date 2022年09月29日 20:54
 * @Description
 */
@SpringBootApplication
@EsMapperScan("com.open.es.mapper")
public class HelloEasyEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloEasyEsApplication.class, args);
    }
}
