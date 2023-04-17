package com.mree.demo.mavidev.common.request;

import com.mree.demo.mavidev.common.BaseCreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CityCreateDto extends BaseCreateDto {

    @NotBlank(message = "Code field cannot be null or empty")
    private String code;

    @NotBlank(message = "Name field cannot be null or empty")
    private String name;

    @NotNull(message = "Country id field cannot be null")
    private UUID countryId;
}
