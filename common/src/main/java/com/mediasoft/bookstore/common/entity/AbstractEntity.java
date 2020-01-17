package com.mediasoft.bookstore.common.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Абстрактная сущность.
 * @param <Id> тип уникального идентификатора сущности.
 */
@MappedSuperclass
public abstract class AbstractEntity<Id extends Serializable> {

    @javax.persistence.Id
    protected Id id;

    public abstract Id getId();

    public abstract void setId(Id id);
}