package com.mediasoft.bookstore.web;

import com.mediasoft.bookstore.book.ejb.BookEjbLocal;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.common.pageable.Sort;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public Book findBookById() {
        book = bookEjbLocal.getBookById(book.getId());
        return book;
    }

    public List<Book> getBooksPage() {
        Integer size = 10;
        return bookEjbLocal.getAllBooks(
                new Pageable(page * size, size, Sort.ASC)
        );
    }
}
