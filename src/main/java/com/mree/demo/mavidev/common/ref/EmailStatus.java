package com.mree.demo.mavidev.common.ref;

import com.mree.demo.mavidev.common.model.BaseEnumDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmailStatus implements BaseEnum {
    PENDING("Pending"),
    SENT("Sent"),
    FAILED("Failed");

    private String desc;

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public BaseEnumDto getDto() {
        return BaseEnumDto.builder().id(name()).value(desc).build();
    }
}
