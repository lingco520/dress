package com.daqsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动
 * @author ANONYM
 */
@EnableCaching
@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("com.daqsoft.mapper")
public class ScenicCenterStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScenicCenterStarterApplication.class, args);
	}
}
