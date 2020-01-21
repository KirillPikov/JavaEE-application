package com.mediasoft.bookstore.author.dao;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.pageable.Pageable;

import java.util.List;

public class AuthorDao extends AbstractAuthorDao {

    @Override
    public List<Author> getAllAuthors(Pageable pageable) {
        return this.namedQuery("getAllAuthors")
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }
}