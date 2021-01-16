package com.mini.calendar;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.mini.db.annotation.EnableRecDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author songjiuhua
 * Created by 2020/12/25 9:54
 */
//@Import(FdfsClientConfig.class)
@EnableRecDB
@SpringBootApplication
public class MiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniApplication.class, args);
    }

}
