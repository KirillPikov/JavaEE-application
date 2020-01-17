package com.mediasoft.bookstore.book.dao;

import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.dao.AbstractDao;

public abstract class AbstractBookDao extends AbstractDao<Book, Long> {

    public AbstractBookDao() {
        super(Book.class);
    }
}