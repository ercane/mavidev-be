package com.mree.demo.mavidev.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BaseUpdateDto extends BaseCreateDto {

    @NotNull(message = "Id field cannot be null")
    private UUID id;
}
