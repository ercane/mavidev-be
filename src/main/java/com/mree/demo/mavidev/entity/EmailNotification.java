package com.mree.demo.mavidev.entity;

import com.mree.demo.mavidev.common.model.EmailNotificationDto;
import com.mree.demo.mavidev.common.ref.EmailStatus;
import com.mree.demo.mavidev.common.ref.MailTemplate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
public class EmailNotification extends BaseEntity<EmailNotificationDto> {

    @Column(length = 500)
    private String to;

    @Enumerated(EnumType.STRING)
    private MailTemplate template;

    private String subject;

    @Column(length = 5000)
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(length = 5000)
    private String result;

    @Override
    public EmailNotificationDto toDto() {
        EmailNotificationDto dto = new EmailNotificationDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    @Override
    public void fromDto(EmailNotificationDto dto) {
        setBody(dto.getBody());
        setResult(dto.getResult());
        setStatus(dto.getStatus());
        setTemplate(dto.getTemplate());
        setTo(dto.getTo());
    }
}
