package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.model.CountryDto;
import com.mree.demo.mavidev.common.request.CountryCreateDto;
import com.mree.demo.mavidev.common.request.CountryUpdateDto;
import com.mree.demo.mavidev.entity.Country;
import com.mree.demo.mavidev.repo.CountryRepository;

public interface CountryService extends BaseService<Country, CountryDto, CountryCreateDto, CountryUpdateDto, CountryRepository>{
}
