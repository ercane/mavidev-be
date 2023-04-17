package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.ref.MailTemplate;
import com.mree.demo.mavidev.common.request.EmailSendRequestDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface EmailNotificationService {


    void sendEmail(EmailSendRequestDto emailSendRequest);
}