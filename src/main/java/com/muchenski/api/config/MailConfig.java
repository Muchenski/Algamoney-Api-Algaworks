package com.muchenski.api.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.muchenski.api.config.property.ApiProperty;

@Configuration
public class MailConfig {

	@Autowired
	private ApiProperty apiProperty;
	
	@Bean
	public JavaMailSender javaMailSender() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 1000 * 10);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setJavaMailProperties(props);
		mailSender.setHost(apiProperty.getMail().getHost());
		mailSender.setPort(apiProperty.getMail().getPort());
		mailSender.setUsername(apiProperty.getMail().getUsername());
		mailSender.setPassword(apiProperty.getMail().getPassword());
		return mailSender;
	}
}
