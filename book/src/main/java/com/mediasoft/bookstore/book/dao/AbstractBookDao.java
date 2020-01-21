package com.mediasoft.bookstore.book.dao;

import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.dao.AbstractDao;
import com.mediasoft.bookstore.common.pageable.Pageable;

import java.util.List;

public abstract class AbstractBookDao extends AbstractDao<Book, Long> {

    public AbstractBookDao() {
        super(Book.class);
    }

    public abstract List<Book> getAllBooks(Pageable pageable);

    public abstract List<Book> getBooksByAuthorId(Long authorId, Pageable pageable);

    public abstract List<Book> getBooksByPublisherId(Long publisherId, Pageable pageable);
}