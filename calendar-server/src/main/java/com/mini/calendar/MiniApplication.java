package com.mini.calendar;

import com.mini.db.annotation.EnableRecDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songjiuhua
 * Created by 2020/12/25 9:54
 */
@EnableRecDB
@SpringBootApplication
public class MiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniApplication.class, args);
    }

}
