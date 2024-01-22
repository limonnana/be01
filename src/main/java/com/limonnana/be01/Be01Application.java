package com.limonnana.be01;

import com.limonnana.be01.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class Be01Application {

	public static void main(String[] args) {
		SpringApplication.run(Be01Application.class, args);
	}

}
