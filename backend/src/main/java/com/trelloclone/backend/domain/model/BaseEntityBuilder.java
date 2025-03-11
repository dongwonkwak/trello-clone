package com.trelloclone.backend.domain.model;

import java.time.LocalDateTime;

public abstract class BaseEntityBuilder<T, B extends BaseEntityBuilder<T, B>> {
    protected Id id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @SuppressWarnings("unchecked")
    public B id(Id id) {
        this.id = id;
        return (B)this;
    }

    @SuppressWarnings("unchecked")
    public B createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return (B)this;
    }

    @SuppressWarnings("unchecked")
    public B updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return (B)this;
    }

    protected void setupDefaults() {
        if (this.id == null) {
            this.id = Id.newId();
        }

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }

        if (this.updatedAt == null) {
            this.updatedAt = this.createdAt;
        }
    }

    public abstract T build();
}
