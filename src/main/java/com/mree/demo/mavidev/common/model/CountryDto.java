package com.mree.demo.mavidev.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mree.demo.mavidev.common.BaseDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDto extends BaseDto {
    private String name;
    private String code;
    private String phoneCode;
    private CommonStatus status;
}
