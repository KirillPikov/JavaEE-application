package com.mediasoft.bookstore.web.controller;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.common.pageable.Sort;
import com.mediasoft.bookstore.web.config.PageSettings;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@Loggable
@RequestScoped
public class AuthorController {

    @Inject
    private AuthorEjbLocal authorEjbLocal;

    private Author author;

    private Integer page;

    @PostConstruct
    public void init() {
        author = new Author();
        page = 0;
    }

    public Author getAuthor() {
        return author;
    }

    public void setPage(Integer page) {
        this.page = page < 0 ? 0 : page;
    }

    public Integer getPage() {
        return page;
    }

    public Author findAuthorById() {
        author = authorEjbLocal.getAuthor(author.getId());
        return author;
    }

    public List<Author> getAuthorsPage() {
        Integer size = PageSettings.TABLE_ROW_COUNT_SIZE;
        return authorEjbLocal.getAuthorsPage(new Pageable(size * page, size, Sort.ASC));
    }

    public String createAuthor() {
        authorEjbLocal.addAuthor(author);
        return "author-info.xhtml";
    }

    public String deleteAuthor() {
        authorEjbLocal.deleteAuthor(author.getId());
        return "author-info.xhtml";
    }

    public String updateAuthor() {
        authorEjbLocal.updateAuthor(author.getId(), author);
        return "author-info.xhtml?id=" + author.getId();
    }
}