package com.mediasoft.bookstore.author.dao;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.dao.AbstractDao;
import com.mediasoft.bookstore.common.pageable.Pageable;

import java.util.List;

public abstract class AbstractAuthorDao extends AbstractDao<Author, Long> {

    public AbstractAuthorDao() {
        super(Author.class);
    }

    public abstract List<Author> getAllAuthors(Pageable pageable);
}