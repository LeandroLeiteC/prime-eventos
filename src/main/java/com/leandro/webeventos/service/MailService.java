package com.leandro.webeventos.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailService {

	@Value("${prime.url}")
	private String url;
	
	private JavaMailSender mailSender;
	private SpringTemplateEngine template;
	
	@Autowired 
	public MailService(JavaMailSender mailSender, SpringTemplateEngine template) {
		this.mailSender = mailSender;
		this.template = template;
	}

	public void enviarConfirmacaoCadastro(String destino, String codigo) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		
		Context context = new Context();
		context.setVariable("titulo", "Bem vindo ao Prime Eventos!");
		context.setVariable("texto", "Precisamos que confirme seu cadastro, clicando no link abaixo.");
		context.setVariable("linkConfirmacao", url+"/confirmacao/cadastro?codigo=" + codigo);
		context.setVariable("textoLink", "Confirmar");
		
		String html = template.process("mail/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Confirmação de cadastro");
		
		helper.addInline("logo", new ClassPathResource("/static/img/PrimeLogo.png"));
		
		mailSender.send(message);
		
	}
	
	public void enviarCodigoRedefinirSenha(String destino, String verificador) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		
		Context context = new Context();
		context.setVariable("titulo", "Recuperação de senha");
		context.setVariable("texto", "Para redefinir sua senha use o código de verificação abaixo:");
		context.setVariable("verificador", verificador);
		
		String html = template.process("mail/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("Redefinição de senha");
		
		helper.addInline("logo", new ClassPathResource("/static/img/PrimeLogo.png"));
		
		mailSender.send(message);
		
	}
}
