package com.muchenski.api.mail;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.domain.Usuario;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;
	
	@Value(value = "${api-property.mail.username}")
	private String remetente;
	
	public void enviarEmail(String remetente, String assunto, String mensagem, List<String> destinatarios) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			messageHelper.setFrom(remetente);
			messageHelper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			messageHelper.setSubject(assunto);
			messageHelper.setText(mensagem, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void enviarEmailTemplate(String remetente, String assunto, String template, List<String> destinatarios,
			Map<String, Object> variaveis) {
		Context webContext = new Context(new Locale("pt", "BR"));
		webContext.setVariables(variaveis);
		String mensagem = templateEngine.process(template, webContext);
		this.enviarEmail(remetente, assunto, mensagem, destinatarios);
	}

	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		List<String> emails = destinatarios.stream().map(d -> d.getEmail()).collect(Collectors.toList());
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("lancamentos", vencidos);
		enviarEmailTemplate(remetente, "Lançamentos vencidos", "aviso_lancamento_vencido", emails, variaveis);
	}
}
