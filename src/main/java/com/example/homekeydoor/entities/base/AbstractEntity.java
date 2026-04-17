package com.example.homekeydoor.entities.base;

import com.example.homekeydoor.consts.LocalDateTimeAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity<T> {

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public abstract T getId();

    public abstract void setId(T id);

    @Override
    public boolean equals(Object obj) {
        T id = this.getId();
        return (id != null && id.equals(((AbstractEntity) obj).getId())) || super.equals(obj);
    }

}
