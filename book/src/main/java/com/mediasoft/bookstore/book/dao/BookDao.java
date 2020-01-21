package com.mediasoft.bookstore.book.dao;

import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.pageable.Pageable;

import java.util.List;

public class BookDao extends AbstractBookDao {

    @Override
    public List<Book> getAllBooks(Pageable pageable) {
        return this.namedQuery("getAllBooks")
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId, Pageable pageable) {
        return this.namedQuery("getBooksByAuthorId")
                .setParameter("author", authorId)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }

    @Override
    public List<Book> getBooksByPublisherId(Long publisherId, Pageable pageable) {
        return this.namedQuery("getBooksByPublisherId")
                .setParameter("publisher", publisherId)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }
}