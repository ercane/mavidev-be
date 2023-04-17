package com.mree.demo.mavidev.service;

import com.mree.demo.mavidev.common.BaseCreateDto;
import com.mree.demo.mavidev.common.BaseDto;
import com.mree.demo.mavidev.common.BaseUpdateDto;
import com.mree.demo.mavidev.entity.BaseEntity;
import com.mree.demo.mavidev.exception.BaseException;
import com.mree.demo.mavidev.repo.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<
        E extends BaseEntity<D>,
        D extends BaseDto,
        C extends BaseCreateDto,
        U extends BaseUpdateDto,
        R extends BaseRepository<E>>
        implements BaseService<E, D,C,U, R> {

    public abstract R getRepo();
    public abstract E getEntity(UUID id);
    public abstract E preCreate(C dto);
    public abstract E preUpdate(U dto);
    public abstract E postCreate(E entity, C dto);
    public abstract E postUpdate(E entity, U dto);
    public abstract E preDelete(UUID id);
    public abstract void postDelete(E entity);

    @Transactional(readOnly = true)
    @Override
    public D get(UUID id) {
        E entity = getEntity(id);
        return entity.toDto();
    }
    @Transactional
    @Override
    public List<D> getAll() {
        List<E> all = getRepo().findAll();
        return all.stream().map(E::toDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public Boolean create(C dto) {
        E entity = preCreate(dto);
        entity = getRepo().save(entity);
        postCreate(entity, dto);
        return true;
    }
    @Transactional
    @Override
    public Boolean update(U dto) {
        E entity = preUpdate(dto);
        entity = getRepo().save(entity);
        postUpdate(entity, dto);
        return true;
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        E entity = preDelete(id);
        entity.setDeletedDate(LocalDateTime.now());
        getRepo().save(entity);
        postDelete(entity);
    }
}
