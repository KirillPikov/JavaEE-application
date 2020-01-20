package com.mediasoft.bookstore.web;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.common.pageable.Sort;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Loggable
@RequestScoped
public class AuthorController {

    @Inject
    private AuthorEjbLocal authorEjbLocal;

    private Author author;

    private Integer page;

    {
        author = new Author();
        page = 0;
    }

    public Author getAuthor() {
        return author;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public Author findAuthorById() {
        author = authorEjbLocal.getAuthor(author.getId());
        return author;
    }

    public List<Author> getAuthorsPage() {
        Integer size = 10;
        return authorEjbLocal.getAuthorsPage(new Pageable(size * page, size, Sort.ASC));
    }
}