package com.promptoven.settlementservice.adaptor.mail.application;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.promptoven.settlementservice.application.port.out.call.MailSending;
import com.promptoven.settlementservice.application.service.dto.PlatformSettlementHistoryDTO;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Setter;

@Slf4j
@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "mailing")
@Setter
public class MailSendingImplWithSpringMail implements MailSending {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 587;

	private final SpringTemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	private List<String> adminAndInvestor;

	@PostConstruct
	private void printInfo() {
		System.out.println(adminAndInvestor);
		System.out.println(Arrays.toString(adminAndInvestor.toArray(new String[0])));
	}

	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(HOST);
		mailSender.setPort(PORT);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", HOST);
		props.put("mail.smtp.ssl.protocols", "TLSv1.3");
		props.put("mail.mime.charset", "UTF-8");
		props.put("mail.smtp.encoding", "UTF-8");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Override
	public void sendMail(PlatformSettlementHistoryDTO platformSettlementHistoryDTO) {
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();
		String body = buildMailBody(platformSettlementHistoryDTO);

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(new InternetAddress(username, "PromptOven", "UTF-8"));
			
			InternetAddress[] addresses = adminAndInvestor.stream()
				.map(email -> {
					try {
						return new InternetAddress(email);
					} catch (MessagingException e) {
						log.error("Invalid email address: " + email, e);
						return null;
					}
				})
				.toArray(InternetAddress[]::new);
				
			helper.setTo(addresses);
			helper.setSubject("일일 결산 보고");
			helper.setText(body, true);

			mailSender.send(message);

		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new MailSendException("Failed to send email", e);
		}
	}

	private String buildMailBody(PlatformSettlementHistoryDTO platformSettlementHistoryDTO) {
		Context context = new Context();
		context.setVariable("recordedAt", platformSettlementHistoryDTO.getRecordedAt().toString());
		context.setVariable("accumulatedSold", platformSettlementHistoryDTO.getAccumulatedSold());
		context.setVariable("accumulatedEarned", platformSettlementHistoryDTO.getAccumulatedEarned());
		context.setVariable("accumulatedSettledForSellerTax",
			platformSettlementHistoryDTO.getAccumulatedSettledForSellerTax());
		context.setVariable("accumulatedSettledForAdminTax",
			platformSettlementHistoryDTO.getAccumulatedSettledForAdminTax());
		context.setVariable("thisYearSettledForAdminTax", platformSettlementHistoryDTO.getThisYearSettledForAdminTax());
		context.setVariable("thisYearSettledForSellerTax",
			platformSettlementHistoryDTO.getThisYearSettledForSellerTax());
		context.setVariable("thisYearEarned", platformSettlementHistoryDTO.getThisYearEarned());
		context.setLocale(Locale.KOREAN);
		return templateEngine.process("BaseMailBody", context);
	}

}