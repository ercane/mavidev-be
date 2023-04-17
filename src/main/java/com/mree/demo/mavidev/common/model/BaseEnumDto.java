package com.mree.demo.mavidev.common.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseEnumDto {
    private String id;
    private String value;

    public static BaseEnumDto getDto(Enum e) {
        return new BaseEnumDto(e.name(), e.name());
    }
}
