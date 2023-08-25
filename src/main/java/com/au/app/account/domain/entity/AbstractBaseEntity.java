package com.au.app.account.domain.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@MappedSuperclass
public class AbstractBaseEntity {

    @Id
    private final String id;

    @Version
    private int version;

    public AbstractBaseEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractBaseEntity other)) {
            return false;
        }
        return getId().equals(other.getId());
    }
}