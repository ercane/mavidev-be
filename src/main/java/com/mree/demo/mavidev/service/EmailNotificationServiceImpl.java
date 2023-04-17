package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.ref.EmailStatus;
import com.mree.demo.mavidev.common.request.EmailSendRequestDto;
import com.mree.demo.mavidev.entity.EmailNotification;
import com.mree.demo.mavidev.repo.EmailNotificationRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

import static com.mree.demo.mavidev.config.RabbitmqConfig.EMAIL_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {


    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    private final EmailNotificationRepository emailNotificationRepository;

    @Value("${spring.mail.from}")
    private String from;

    @RabbitListener(queues = EMAIL_QUEUE)
    @Override
    public void sendEmail(EmailSendRequestDto emailSendRequest) {
        try {
            log.info("Email send operation started. Data: {}", emailSendRequest);
            String emailBody = createEmailBody(emailSendRequest);

            EmailNotification notification = new EmailNotification();
            notification.setBody(emailBody);
            notification.setSubject(emailSendRequest.getTemplate().getSubject());
            notification.setTemplate(emailSendRequest.getTemplate());
            notification.setTo(String.join(",", emailSendRequest.getTo()));
            notification.setStatus(EmailStatus.PENDING);
            notification = emailNotificationRepository.save(notification);

            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(emailSendRequest.getTo().toArray(String[]::new));
                helper.setFrom(from);
                helper.setSubject(emailSendRequest.getTemplate().getSubject());
                helper.setText(emailBody, true);
                javaMailSender.send(mimeMessage);
                notification.setStatus(EmailStatus.SENT);
                notification.setResult("Email sent successfully");
                log.info("Email send operation completed. Data: {}", emailSendRequest);
            } catch (Exception e) {
                log.error("Email send operation failed. Data: {}", emailSendRequest, e);
                notification.setStatus(EmailStatus.FAILED);
                notification.setResult(e.getMessage());
            }
            emailNotificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Email send operation failed. Data: {}", emailSendRequest, e);
        }
    }

    private String createEmailBody(EmailSendRequestDto emailSendRequest) {
        Context context = new Context();
        emailSendRequest.getParams().forEach(context::setVariable);
        return templateEngine.process(emailSendRequest.getTemplate().name().toLowerCase(Locale.UK), context);
    }
}