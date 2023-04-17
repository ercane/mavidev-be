package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.common.request.CityCreateDto;
import com.mree.demo.mavidev.common.request.CityUpdateDto;
import com.mree.demo.mavidev.config.AppProperties;
import com.mree.demo.mavidev.entity.City;
import com.mree.demo.mavidev.entity.Country;
import com.mree.demo.mavidev.exception.CityAlreadyExistException;
import com.mree.demo.mavidev.exception.CityNotFoundException;
import com.mree.demo.mavidev.repo.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CityServiceTest {


    CityServiceImpl cityService;

    @MockBean
    CountryService countryService;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    RabbitTemplate rabbitTemplate;

    @MockBean
    AppProperties appProperties;

    @BeforeEach
    public void init() {
        cityService = new CityServiceImpl(cityRepository, countryService, appProperties, rabbitTemplate);
    }

    @Test
    void getEntity() {
        UUID uuid = UUID.randomUUID();

        Country country1 = new Country();
        country1.setId(uuid);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);

        City city1 = new City();
        city1.setId(uuid);
        city1.setCode("IST");
        city1.setName("Istanbul");
        city1.setCountry(country1);
        city1.setStatus(CommonStatus.ACTIVE);

        when(cityRepository.findById(uuid)).thenReturn(Optional.of(city1));

        assertEquals(city1, cityService.getEntity(uuid));
        assertThrows(CityNotFoundException.class, () -> cityService.getEntity(UUID.randomUUID()));
    }

    @Test
    void preCreate() {
        UUID uuid = UUID.randomUUID();
        Country country1 = new Country();
        country1.setId(uuid);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);

        City city1 = new City();
        city1.setCode("IST");
        city1.setName("Istanbul");
        city1.setCountry(country1);
        city1.setStatus(CommonStatus.ACTIVE);

        CityCreateDto cityCreateDto = new CityCreateDto();
        cityCreateDto.setCode("IST");
        cityCreateDto.setName("Istanbul");
        cityCreateDto.setCountryId(uuid);

        when(cityRepository.findByCodeIgnoreCaseAndCountryId(cityCreateDto.getCode(), cityCreateDto.getCountryId())).thenReturn(Optional.empty());
        when(countryService.getEntity(cityCreateDto.getCountryId())).thenReturn(country1);
        assertEquals(city1.getCode(), cityService.preCreate(cityCreateDto).getCode());

        when(cityRepository.findByCodeIgnoreCaseAndCountryId(cityCreateDto.getCode(), cityCreateDto.getCountryId())).thenReturn(Optional.of(city1));
        assertThrows(CityAlreadyExistException.class, () -> cityService.preCreate(cityCreateDto));
    }

    @Test
    void preUpdate() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Country country1 = new Country();
        country1.setId(uuid1);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);


        Country country2 = new Country();
        country2.setId(uuid2);
        country2.setCode("US");
        country2.setName("United States");
        country2.setStatus(CommonStatus.ACTIVE);

        City city1 = new City();
        city1.setId(uuid1);
        city1.setCode("IST");
        city1.setName("Istanbul");
        city1.setCountry(country1);
        city1.setStatus(CommonStatus.ACTIVE);

        City city2 = new City();
        city2.setId(uuid1);
        city2.setCode("IST");
        city2.setName("IST");
        city2.setCountry(country2);
        city2.setStatus(CommonStatus.PASSIVE);

        CityUpdateDto cityUpdateDto = new CityUpdateDto();
        cityUpdateDto.setId(uuid1);
        cityUpdateDto.setCode("IST");
        cityUpdateDto.setName("IST");
        cityUpdateDto.setCountryId(uuid2);
        cityUpdateDto.setStatus(CommonStatus.PASSIVE);

        when(cityRepository.findById(uuid1)).thenReturn(Optional.of(city1));
        when(countryService.getEntity(cityUpdateDto.getCountryId())).thenReturn(country2);
        when(cityRepository.save(any(City.class))).thenReturn(city2);
        assertEquals(city2.getName(), cityService.preUpdate(cityUpdateDto).getName());

        when(cityRepository.findById(uuid1)).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class, () -> cityService.preUpdate(cityUpdateDto));
    }

    @Test
    void postCreate() {
    }

    @Test
    void postUpdate() {
    }

    @Test
    void getByCountryId() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        Country country1 = new Country();
        country1.setId(uuid1);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);


        Country country2 = new Country();
        country2.setId(uuid2);
        country2.setCode("US");
        country2.setName("United States");
        country2.setStatus(CommonStatus.ACTIVE);

        City city1 = new City();
        city1.setId(uuid1);
        city1.setCode("IST");
        city1.setName("Istanbul");
        city1.setCountry(country1);
        city1.setStatus(CommonStatus.ACTIVE);

        City city2 = new City();
        city2.setId(uuid2);
        city2.setCode("IST");
        city2.setName("IST");
        city2.setCountry(country2);
        city2.setStatus(CommonStatus.PASSIVE);

        when(cityRepository.findByCountryId(uuid1)).thenReturn(List.of(city1));
        when(cityRepository.findByCountryId(uuid2)).thenReturn(List.of(city1, city2));
        when(cityRepository.findByCountryId(UUID.randomUUID())).thenReturn(List.of());

        assertEquals(1, cityService.getByCountryId(uuid1).size());
        assertEquals(2, cityService.getByCountryId(uuid2).size());
        assertEquals(0, cityService.getByCountryId(UUID.randomUUID()).size());
    }
}