package com.mree.demo.mavidev.common.model;

import com.mree.demo.mavidev.common.BaseDto;
import com.mree.demo.mavidev.common.ref.EmailStatus;
import com.mree.demo.mavidev.common.ref.MailTemplate;
import lombok.Data;

@Data
public class EmailNotificationDto extends BaseDto {

    private String to;

    private MailTemplate template;

    private String subject;

    private String body;

    private EmailStatus status;

    private String result;

}
