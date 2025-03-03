package com.trelloclone.backend.domain.model.common;

import java.time.LocalDateTime;


import static java.util.Objects.requireNonNull;

public abstract class BaseEntity {
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
    }

    protected void recordUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
