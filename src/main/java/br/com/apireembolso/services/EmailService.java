package br.com.apireembolso.services;


import java.time.LocalDateTime;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import br.com.apireembolso.enums.StatusEmail;
import br.com.apireembolso.models.Email;
import br.com.apireembolso.respositories.EmailRepo;


@Service
@Component
public class EmailService {

    @Value("${repita-seu-email}")
    private String email;

	@Autowired
    EmailRepo emailRepository;

    @Autowired
    private JavaMailSender emailSender;


    public Email sendEmail(Email emailModel) {
    emailModel.setSendDateEmail(LocalDateTime.now());
    try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());
        emailSender.send(message);

        emailModel.setStatusEmail(StatusEmail.SENT);
    } catch (MailException e){
        emailModel.setStatusEmail(StatusEmail.ERROR);
    } finally {
        return emailRepository.save(emailModel);
    }
}
}
    
