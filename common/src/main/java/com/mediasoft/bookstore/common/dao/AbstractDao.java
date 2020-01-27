package com.mediasoft.bookstore.common.dao;

import com.mediasoft.bookstore.common.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;

/**
 * Абстрактный класс описывающий операции со всеми сущностями.
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractDao<Entity extends AbstractEntity, Id extends Serializable> {

    @PersistenceContext(unitName = "bookstore")
    protected EntityManager entityManager;

    private Class<Entity> entityClass;

    public Class<Entity> getEntityClass() {
        return entityClass;
    }

    public AbstractDao(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Выполняет поиск сущности по её ID.
     * @param id ID сущности.
     * @return
     */
    public Optional<Entity> findById(Id id) {
        Entity entity = entityManager.find(entityClass, id);
        return Optional.of(entity);
    };

    /**
     * Выполняет сохранение сущности в БД.
     * @param entity
     * @return
     */
    public void save(Entity entity) {
        entityManager.persist(entity);
    }

    /**
     * Выполняет обновление состояния сущности в БД.
     * @param entity
     */
    public void update(Entity entity) {
        entityManager.merge(entity);
    }

    /**
     * Выполняет удаление сущности из БД по ID.
     * @param id
     */
    public void deleteById(Id id) {
        Entity entity = entityManager.find(entityClass, id);
        delete(entity);
    }

    /**
     * Выполняет удаление сущности из БД.
     * @param entity
     */
    public void delete(Entity entity) {
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public boolean existsById(Id id) {
        return entityManager.find(entityClass, id) != null;
    }

    public TypedQuery<Entity> namedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName, entityClass);
    }
}