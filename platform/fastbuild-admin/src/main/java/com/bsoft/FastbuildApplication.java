package com.bsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author fastbuild
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.bsoft.system.mapper")
public class FastbuildApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FastbuildApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  创业管理平台启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " _                __ _     \n" +
                "| |              / _| |    \n" +
                "| |__  ___  ___ | |_| |_   \n" +
                "| '_ \\/ __|/ _ \\|  _| __|  \n" +
                "| |_) \\__ \\ (_) | | | |_   \n" +
                "|_.__/|___/\\___/|_|  \\__|  ");
    }
}
