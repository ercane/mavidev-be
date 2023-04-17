package com.mree.demo.mavidev.common.request;

import com.mree.demo.mavidev.common.BaseCreateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CountryCreateDto extends BaseCreateDto {

    @NotBlank(message = "Code field cannot be null or empty")
    private String code;

    @NotBlank(message = "Name field cannot be null or empty")
    private String name;

    @NotBlank(message = "Phone code field cannot be null or empty")
    private String phoneCode;

}
