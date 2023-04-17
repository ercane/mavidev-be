package com.mree.demo.mavidev.repo;

import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.entity.Country;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends BaseRepository<Country> {
    Optional<Country> findByCodeIgnoreCase(String code);

    Optional<Country> findByIdNotAndCodeIgnoreCase(UUID id, String code);

    List<Country> findByStatus(CommonStatus commonStatus);
}
