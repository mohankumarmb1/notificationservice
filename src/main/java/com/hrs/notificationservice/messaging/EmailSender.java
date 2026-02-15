package com.hrs.notificationservice.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSender {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String email, String subject, String content)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("hrs.admin@gmail.com", "Admin");
		helper.setTo(email);

		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}

}
