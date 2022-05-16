package com.jack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.jack.dao"})
public class dhxGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(dhxGameApplication.class, args);
    }

}
