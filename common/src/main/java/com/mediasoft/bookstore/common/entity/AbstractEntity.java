package com.mediasoft.bookstore.common.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Абстрактная сущность.
 * @param <Id> тип уникального идентификатора сущности.
 */
@MappedSuperclass
public abstract class AbstractEntity<Id extends Serializable> implements Serializable {

    @javax.persistence.Id
    @Access(AccessType.PROPERTY)
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="common_seq")
    protected Id id;

    public abstract Id getId();

    public abstract void setId(Id id);
}