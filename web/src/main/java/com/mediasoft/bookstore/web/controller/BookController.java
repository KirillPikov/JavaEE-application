package com.mediasoft.bookstore.web.controller;

import com.mediasoft.bookstore.book.ejb.BookEjbLocal;
import com.mediasoft.bookstore.book.entity.Book;
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
public class BookController {

    @Inject
    private BookEjbLocal bookEjbLocal;

    private Book book;

    private Integer page;

    @PostConstruct
    public void init() {
        book = new Book();
        page = 0;
    }

    public Book getBook() {
        return book;
    }

    public void setPage(Integer page) {
        this.page = page < 0 ? 0 : page;
    }

    public Integer getPage() {

        return page;
    }

    public Book findBookById() {
        book = bookEjbLocal.getBookById(book.getId());
        return book;
    }

    public List<Book> getBooksPage() {
        Integer size = PageSettings.TABLE_ROW_COUNT_SIZE;
        return bookEjbLocal.getAllBooks(
                new Pageable(page * size, size, Sort.ASC)
        );
    }

    public String createBook() {
        bookEjbLocal.addBook(book);
        return "books-page.xhtml";
    }
}