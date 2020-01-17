package com.mediasoft.bookstore.author.dao;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.dao.AbstractDao;

public abstract class AbstractAuthorDao extends AbstractDao<Author, Long> {

    public AbstractAuthorDao() {
        super(Author.class);
    }
}