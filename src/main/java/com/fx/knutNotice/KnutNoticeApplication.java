package com.fx.knutNotice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KnutNoticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnutNoticeApplication.class, args);
	}

}
