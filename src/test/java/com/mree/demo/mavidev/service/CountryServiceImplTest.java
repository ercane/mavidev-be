package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.common.request.CountryCreateDto;
import com.mree.demo.mavidev.common.request.CountryUpdateDto;
import com.mree.demo.mavidev.config.AppProperties;
import com.mree.demo.mavidev.entity.Country;
import com.mree.demo.mavidev.exception.CountryAlreadyExistException;
import com.mree.demo.mavidev.exception.CountryNotFoundException;
import com.mree.demo.mavidev.repo.CityRepository;
import com.mree.demo.mavidev.repo.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceImplTest {

    @MockBean
    CountryRepository countryRepository;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    CountryServiceImpl countryService;

    @MockBean
    RabbitTemplate rabbitTemplate;

    @MockBean
    AppProperties appProperties;

    @BeforeEach
    public void init() {
        countryService = new CountryServiceImpl(countryRepository, cityRepository, rabbitTemplate, appProperties);
    }

    @Test
    void getEntity() {
        UUID uuid = UUID.randomUUID();
        Country country1 = new Country();
        country1.setId(uuid);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);

        when(countryRepository.findById(uuid)).thenReturn(Optional.of(country1));

        assertEquals(country1, countryService.getEntity(uuid));
        assertThrows(CountryNotFoundException.class, () -> countryService.getEntity(UUID.randomUUID()));
    }

    @Test
    void preCreate() {
        Country country1 = new Country();
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);

        CountryCreateDto countryCreateDto = new CountryCreateDto();
        countryCreateDto.setCode("TR");
        countryCreateDto.setName("Turkey");

        when(countryRepository.findByCodeIgnoreCase(countryCreateDto.getCode())).thenReturn(Optional.of(country1));
        assertThrows(CountryAlreadyExistException.class, () -> countryService.preCreate(countryCreateDto));
        when(countryRepository.findByCodeIgnoreCase(countryCreateDto.getCode())).thenReturn(Optional.empty());
        assertEquals(country1.getCode(), countryService.preCreate(countryCreateDto).getCode());
    }

    @Test
    void preUpdate() {
        UUID uuid1 = UUID.randomUUID();

        Country country1 = new Country();
        country1.setId(uuid1);
        country1.setCode("TR");
        country1.setName("Turkey");
        country1.setStatus(CommonStatus.ACTIVE);


        Country country2 = new Country();
        country2.setId(uuid1);
        country2.setCode("US");
        country2.setName("United States");
        country2.setStatus(CommonStatus.PASSIVE);

        CountryUpdateDto countryUpdateDto = new CountryUpdateDto();
        countryUpdateDto.setId(uuid1);
        countryUpdateDto.setCode("US");
        countryUpdateDto.setName("United States");
        countryUpdateDto.setStatus(CommonStatus.PASSIVE);


        when(countryRepository.findById(countryUpdateDto.getId())).thenReturn(Optional.of(country1));
        when(countryRepository.save(any(Country.class))).thenReturn(country2);
        assertEquals(country2, countryService.preUpdate(countryUpdateDto));

        when(countryRepository.findById(uuid1)).thenReturn(Optional.empty());
        assertThrows(CountryNotFoundException.class, () -> countryService.preUpdate(countryUpdateDto));
    }

    @Test
    void postCreate() {
    }

    @Test
    void postUpdate() {
    }
}