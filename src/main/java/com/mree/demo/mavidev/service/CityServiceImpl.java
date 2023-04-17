package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.model.CityDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.common.ref.MailTemplate;
import com.mree.demo.mavidev.common.request.CityCreateDto;
import com.mree.demo.mavidev.common.request.CityUpdateDto;
import com.mree.demo.mavidev.common.request.EmailSendRequestDto;
import com.mree.demo.mavidev.config.AppProperties;
import com.mree.demo.mavidev.entity.City;
import com.mree.demo.mavidev.entity.Country;
import com.mree.demo.mavidev.exception.CityAlreadyExistException;
import com.mree.demo.mavidev.exception.CityNotFoundException;
import com.mree.demo.mavidev.repo.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mree.demo.mavidev.config.RabbitmqConfig.EMAIL_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl extends BaseServiceImpl<City, CityDto, CityCreateDto, CityUpdateDto, CityRepository> implements CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final AppProperties appProperties;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public CityRepository getRepo() {
        return cityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public City getEntity(UUID id) {
        return getRepo().findById(id).orElseThrow(CityNotFoundException::new);
    }

    @Override
    public List<CityDto> getByStatus(String status) {
        CommonStatus commonStatus = CommonStatus.valueOf(status);
        return getRepo().findByStatus(commonStatus)
                .stream().map(City::toDto).collect(Collectors.toList());
    }

    @Override
    public City preCreate(CityCreateDto dto) {
        log.info("City create operation started. Data: {}", dto);

        Optional<City> optCity = cityRepository.findByCodeIgnoreCaseAndCountryId(dto.getCode(), dto.getCountryId());
        if (optCity.isPresent()) {
            throw new CityAlreadyExistException();
        }

        Country country = countryService.getEntity(dto.getCountryId());
        CityDto cityDto = new CityDto();
        BeanUtils.copyProperties(dto, cityDto);

        City city = new City();
        city.fromDto(cityDto);
        city.setCountry(country);
        city.setStatus(CommonStatus.ACTIVE);

        return city;
    }

    @Override
    public City preUpdate(CityUpdateDto dto) {
        log.info("City update operation started. Data: {}", dto);

        City city = getEntity(dto.getId());

        Optional<City> optCity = cityRepository.findByIdNotAndCountryIdAndCode(dto.getId(), dto.getCountryId(), dto.getCode());
        if (optCity.isPresent()) {
            throw new CityAlreadyExistException();
        }

        CityDto cityDto = new CityDto();
        BeanUtils.copyProperties(dto, cityDto);

        city.fromDto(cityDto);

        if (city.getCountry().getId().equals(dto.getCountryId())) {
            return getRepo().save(city);
        }

        Country country = countryService.getEntity(dto.getCountryId());
        city.setCountry(country);
        return city;
    }

    @Override
    public City postCreate(City entity, CityCreateDto dto) {
        log.info("City create operation finished. Id: {} Data: {}", entity.getId(), dto);

        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.CITY_CREATED)
                .params(Map.of("name", entity.getName()))
                .build());

        return entity;
    }

    @Override
    public City postUpdate(City entity, CityUpdateDto dto) {
        log.info("City update operation finished. Id: {} Data: {}", entity.getId(), dto);

        Map<String, String> params = Map.of(
                "name", entity.getName(),
                "code", entity.getCode(),
                "status", entity.getStatus().name(),
                "updatedDate", entity.getUpdatedDate().toString(),
                "country", entity.getCountry().getName());
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.CITY_CREATED)
                .params(params)
                .build());
        return entity;
    }


    @Override
    public City preDelete(UUID id) {
        log.info("City delete operation started. Id: {}", id);
        return getEntity(id);
    }

    @Override
    public void postDelete(City entity) {
        log.info("City delete operation finished. Id: {}", entity.getId());
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.CITY_DELETED)
                .params(Map.of("name", entity.getName()))
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CityDto> getByCountryId(UUID countryId) {
        List<City> all = getRepo().findByCountryId(countryId);
        return all.stream().map(City::toDto).toList();
    }
}
