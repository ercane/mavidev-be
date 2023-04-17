package com.mree.demo.mavidev.repo;

import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends BaseRepository<City> {
    Optional<City> findByCodeIgnoreCaseAndCountryId(String code, UUID countryId);

    List<City> findByCountryId(UUID countryId);

    Optional<City> findByIdNotAndCountryIdAndCode(UUID id, UUID countryId, String code);

    List<City> findByStatus(CommonStatus commonStatus);
}
