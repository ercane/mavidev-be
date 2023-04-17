package com.mree.demo.mavidev.common.request;

import com.mree.demo.mavidev.common.BaseUpdateDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountryUpdateDto extends BaseUpdateDto {

    @NotBlank(message = "Code field cannot be null or empty")
    private String code;

    @NotBlank(message = "Name field cannot be null or empty")
    private String name;

    @NotBlank(message = "Phone code field cannot be null or empty")
    private String phoneCode;

    @NotNull(message = "Status field cannot be null")
    private CommonStatus status;
}
