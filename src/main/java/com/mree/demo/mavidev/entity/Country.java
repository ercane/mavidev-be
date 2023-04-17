package com.mree.demo.mavidev.entity;

import com.mree.demo.mavidev.common.model.CountryDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.util.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = Constants.DELETE_DATE_CLAUSE)
public class Country extends BaseEntity<CountryDto> {

    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneCode;

    @Enumerated(EnumType.STRING)
    private CommonStatus status;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<City> cities;

    @Override
    public CountryDto toDto() {
        CountryDto dto = new CountryDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    @Override
    public void fromDto(CountryDto dto) {
        setCode(dto.getCode());
        setName(dto.getName());
        setPhoneCode(dto.getPhoneCode());
        setStatus(dto.getStatus());
    }
}
