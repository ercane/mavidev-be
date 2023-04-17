package com.mree.demo.mavidev.common.request;

import com.mree.demo.mavidev.common.ref.MailTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSendRequestDto {

    private MailTemplate template;

    private List<String> to;

    private Map<String, String> params;
}
