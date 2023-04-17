package com.mree.demo.mavidev.common.request;

import com.mree.demo.mavidev.common.BaseUpdateDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CityUpdateDto extends BaseUpdateDto {
    @NotBlank(message = "Code field cannot be null or empty")
    private String code;

    @NotBlank(message = "Name field cannot be null or empty")
    private String name;

    @NotNull(message = "Country id field cannot be null")
    private UUID countryId;

    @NotNull(message = "Status field cannot be null")
    private CommonStatus status;
}
