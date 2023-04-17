package com.mree.demo.mavidev.repo;

import com.mree.demo.mavidev.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, UUID> {
}
