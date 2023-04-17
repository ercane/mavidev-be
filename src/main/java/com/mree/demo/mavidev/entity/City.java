package com.mree.demo.mavidev.entity;

import com.mree.demo.mavidev.common.model.CityDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
@Where(clause = Constants.DELETE_DATE_CLAUSE)
public class City extends BaseEntity<CityDto> {

    private String name;
    private String code;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Country country;

    @Override
    public CityDto toDto() {
        CityDto dto = new CityDto();
        BeanUtils.copyProperties(this, dto);
        if (country != null) {
            dto.setCountry(country.toDto());
        }
        return dto;
    }

    @Override
    public void fromDto(CityDto dto) {
        setCode(dto.getCode());
        setName(dto.getName());
        setStatus(dto.getStatus());
    }
}
