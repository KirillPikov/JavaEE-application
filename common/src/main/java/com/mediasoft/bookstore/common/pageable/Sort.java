package com.mediasoft.bookstore.common.pageable;

import java.io.Serializable;

/**
 * Вид сортировки.
 */
public enum  Sort implements Serializable {
    /**
     * Cортировка по возрастанию.
     */
    ASC,

    /**
     * Сортировка по убыванию.
     */
    DESC
}