package com.leandro.webeventos.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

//	@Value("${prime.mail.host}")
//	private String host;
//	
//	@Value("${prime.mail.port}")
//	private Integer port;
//	
//	@Value("${prime.mail.username}")
//	private String username;
//	
//	@Value("${prime.mail.password}")
//	private String password;
//	
//    @Bean
//    public JavaMailSender javaMailSender() {
//        Properties props = new Properties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth","true");
//        props.put("mail.smtp.starttls.enable","true");
//        props.put("mail.smtp.connectiontimeout", 10000);
//        props.put("mail.smtp.ssl.enable", "true");
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setJavaMailProperties(props);
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        return mailSender;
//    }
}
