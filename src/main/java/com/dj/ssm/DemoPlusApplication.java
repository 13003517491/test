package com.dj.ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dj.ssm.mapper")
public class DemoPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPlusApplication.class, args);
    }

}
