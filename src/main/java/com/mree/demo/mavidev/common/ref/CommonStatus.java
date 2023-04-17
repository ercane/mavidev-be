package com.mree.demo.mavidev.common.ref;

import com.mree.demo.mavidev.common.model.BaseEnumDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommonStatus implements BaseEnum {
    ACTIVE("Aktif"),
    PASSIVE("Pasif"),
    LOCKED("Kilitli");

    @Getter
    private String description;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BaseEnumDto getDto() {
        return new BaseEnumDto(this.name(), description);
    }
}
