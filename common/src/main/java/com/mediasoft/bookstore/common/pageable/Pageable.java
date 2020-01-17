package com.mediasoft.bookstore.common.pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Pageable implements Serializable {

    private final Integer offset;

    private final Integer size;

    private final Sort sort;
}