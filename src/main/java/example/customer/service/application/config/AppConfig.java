package example.customer.service.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import example.customer.service.util.AWSEmailSender;

@Configuration
@ComponentScan("example.customer.service")
public class AppConfig {
	
	@Bean
	public AWSEmailSender getAWSEmailSender(){
		return new AWSEmailSender();
	}
	
}
