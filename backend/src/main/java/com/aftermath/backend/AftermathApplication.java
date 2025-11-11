package com.aftermath.backend;

import com.aftermath.backend.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.sql.DataSource;

@SpringBootApplication()
@EnableConfigurationProperties(AppProperties.class)
public class AftermathApplication {

	public static void main(String[] args) {
		System.out.println("Hello? : " + System.currentTimeMillis());
		SpringApplication.run(AftermathApplication.class, args);
	}

}
