package com.mediasoft.bookstore.publisher.entity;

import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Publisher extends AbstractEntity<Long> implements Serializable {

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private List<Book> books;

    @Override
    @SequenceGenerator(name = "common_seq", sequenceName = "common_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_seq")
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}