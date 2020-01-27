package com.mediasoft.bookstore.book.entity;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.entity.AbstractEntity;
import com.mediasoft.bookstore.publisher.entity.Publisher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "getAllBooks", query = "SELECT b FROM Book b"),
        @NamedQuery(name = "getBooksByAuthorId", query = "SELECT b FROM Book b JOIN b.author a WHERE b.author = a AND a = :author"),
        @NamedQuery(name = "getBooksByPublisherId", query = "SELECT b FROM Book b JOIN b.publisher p WHERE b.publisher = p AND p = :publisher")
})
public class Book extends AbstractEntity<Long> implements Serializable {

    private String isbn;

    @ManyToOne
    @JoinColumn
    private Publisher publisher;

    @ManyToOne
    @JoinColumn
    private Author author;

    private Integer year;

    private String title;

    private Integer price;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}