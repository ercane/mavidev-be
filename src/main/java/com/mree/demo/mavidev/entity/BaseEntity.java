package com.mree.demo.mavidev.entity;

import com.mree.demo.mavidev.common.BaseDto;
import com.mree.demo.mavidev.common.ref.CommonStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<D extends BaseDto> {

    @Id
    @GeneratedValue
    protected UUID id;

    @Getter
    @Setter
    @CreatedDate
    @Column(nullable = false, insertable = false)
    @ColumnDefault("current_timestamp")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Getter
    @Setter
    @LastModifiedDate
    @Column(nullable = false, insertable = false)
    @ColumnDefault("current_timestamp")
    private LocalDateTime updatedDate = LocalDateTime.now();

    private LocalDateTime deletedDate;

    @Version
    @ColumnDefault("0")
    private Long version;

    public abstract D toDto();
    public abstract void fromDto(D dto);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseEntity)) {
            return false;
        }

        BaseEntity<?> other = (BaseEntity<?>) obj;
        return id != null && id.equals(other.id);
    }
}
