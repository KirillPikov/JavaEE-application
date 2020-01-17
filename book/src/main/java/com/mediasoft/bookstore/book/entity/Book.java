package com.mediasoft.bookstore.book.entity;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.publisher.entity.Publisher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Book implements Serializable {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

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
}