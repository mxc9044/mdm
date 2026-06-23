package com.mdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan({"com.mdm.**.mapper", "com.mdm.**.mapping", "com.mdm.**.inbound"})
public class MdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmApplication.class, args);
    }
}
