package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.model.CityDto;
import com.mree.demo.mavidev.common.request.CityCreateDto;
import com.mree.demo.mavidev.common.request.CityUpdateDto;
import com.mree.demo.mavidev.entity.City;
import com.mree.demo.mavidev.repo.CityRepository;

import java.util.List;
import java.util.UUID;

public interface CityService extends BaseService<City, CityDto, CityCreateDto, CityUpdateDto, CityRepository> {
    List<CityDto> getByCountryId(UUID countryId);
}
