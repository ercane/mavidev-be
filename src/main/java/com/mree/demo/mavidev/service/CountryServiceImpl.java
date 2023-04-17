package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.model.CountryDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.common.ref.MailTemplate;
import com.mree.demo.mavidev.common.request.CountryCreateDto;
import com.mree.demo.mavidev.common.request.CountryUpdateDto;
import com.mree.demo.mavidev.common.request.EmailSendRequestDto;
import com.mree.demo.mavidev.config.AppProperties;
import com.mree.demo.mavidev.entity.Country;
import com.mree.demo.mavidev.exception.CountryAlreadyExistException;
import com.mree.demo.mavidev.exception.CountryNotFoundException;
import com.mree.demo.mavidev.repo.CityRepository;
import com.mree.demo.mavidev.repo.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mree.demo.mavidev.config.RabbitmqConfig.EMAIL_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl extends BaseServiceImpl<Country, CountryDto, CountryCreateDto, CountryUpdateDto, CountryRepository> implements CountryService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final RabbitTemplate rabbitTemplate;
    private final AppProperties appProperties;

    @Override
    public CountryRepository getRepo() {
        return countryRepository;
    }

    @Override
    public Country getEntity(UUID id) {
        return getRepo().findById(id).orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public List<CountryDto> getByStatus(String status) {
        CommonStatus commonStatus = CommonStatus.valueOf(status);
        return getRepo().findByStatus(commonStatus)
                .stream().map(Country::toDto).collect(Collectors.toList());
    }

    @Override
    public Country preCreate(CountryCreateDto dto) {
        log.info("Country create operation started. Data: {}", dto);

        Optional<Country> optCountry = getRepo().findByCodeIgnoreCase(dto.getCode());
        if (optCountry.isPresent()) {
            throw new CountryAlreadyExistException();
        }

        Country country = new Country();
        CountryDto countryDto = new CountryDto();
        BeanUtils.copyProperties(dto, countryDto);

        country.fromDto(countryDto);
        country.setStatus(CommonStatus.ACTIVE);
        return country;
    }

    @Override
    public Country preUpdate(CountryUpdateDto dto) {
        log.info("Country update operation started. Data: {}", dto);

        Country country = getEntity(dto.getId());

        Optional<Country> optCountry = countryRepository.findByIdNotAndCodeIgnoreCase(dto.getId(), dto.getCode());
        if (optCountry.isPresent()) {
            throw new CountryAlreadyExistException();
        }

        CountryDto countryDto = new CountryDto();
        BeanUtils.copyProperties(dto, countryDto);

        country.fromDto(countryDto);
        return country;
    }

    @Override
    public Country postCreate(Country entity, CountryCreateDto dto) {
        log.info("Country create operation finished. Entity: {} Data: {}", entity.getId(), dto);
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.COUNTRY_CREATED)
                .params(Map.of("name", entity.getName()))
                .build());
        return entity;
    }

    @Override
    public Country postUpdate(Country entity, CountryUpdateDto dto) {
        log.info("Country update operation finished. Entity: {} Data: {}", entity.getId(), dto);

        Map<String, String> params = Map.of(
                "name", entity.getName(),
                "code", entity.getCode(),
                "status", entity.getStatus().name(),
                "updatedDate", entity.getUpdatedDate().toString(),
                "phoneCode", entity.getPhoneCode());
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.CITY_CREATED)
                .params(params)
                .build());

        return entity;
    }


    @Override
    public Country preDelete(UUID id) {
        log.info("Country delete operation started. Id: {}", id);
        Country country = getEntity(id);
        country.getCities().forEach(c -> {
            c.setDeletedDate(LocalDateTime.now());
            cityRepository.save(c);
        });
        return country;
    }

    @Override
    public void postDelete(Country entity) {
        log.info("Country delete operation finished. Id: {}", entity.getId());
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, EmailSendRequestDto.builder()
                .to(appProperties.getNotificationReceivers())
                .template(MailTemplate.COUNTRY_DELETED)
                .params(Map.of("name", entity.getName()))
                .build());

    }


}
