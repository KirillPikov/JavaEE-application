package com.mediasoft.bookstore.common.pageable;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Pageable implements Serializable {

    /**
     * Смещение относитльно первой найденной строки.
     * Должно быть >= 0.
     */
    private final Integer offset;

    /**
     * Размер выборки.
     * Должен быть >= 0.
     */
    private final Integer size;

    /**
     * Тип сортировки.
     */
    private final Sort sort;

    /**
     * Конструтор с значениями.
     * @param offset
     * @param size
     * @param sort
     */
    public Pageable(Integer offset, Integer size, Sort sort) {
        this.offset = Objects.isNull(offset) || offset < 0 ? PaginationSettings.DEFAULT_OFFSET : offset;
        this.size   = Objects.isNull(size)   || size < 0   ? PaginationSettings.DEFAULT_SIZE   : size;
        this.sort   = Objects.isNull(sort) ? Sort.ASC : sort;
    }

    /**
     * Конструктор по умолчанию.
     */
    public Pageable() {
        this.offset = PaginationSettings.DEFAULT_OFFSET;
        this.size   = PaginationSettings.DEFAULT_SIZE;
        this.sort   = Sort.ASC;
    }
}