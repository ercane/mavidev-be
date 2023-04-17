package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.BaseCreateDto;
import com.mree.demo.mavidev.common.BaseDto;
import com.mree.demo.mavidev.common.BaseUpdateDto;
import com.mree.demo.mavidev.common.ref.BaseEnum;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import com.mree.demo.mavidev.entity.BaseEntity;
import com.mree.demo.mavidev.repo.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface BaseService<
        E extends BaseEntity<D>,
        D extends BaseDto,
        C extends BaseCreateDto,
        U extends BaseUpdateDto,
        R extends BaseRepository<E>> {
    E getEntity(UUID id);

    D get(UUID id);

    List<D> getAll();

    Boolean create(C dto);

    Boolean update(U dto);

    void delete(UUID id);

    List<D> getByStatus(String status);
}
