package com.mediasoft.bookstore.web;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.author.entity.Author;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AuthorController {

    @Inject
    private AuthorEjbLocal authorEjbLocal;

    private Author author;

    public Author getAuthor() {
        author = authorEjbLocal.getAuthor(26L);
        return author;
    }
}