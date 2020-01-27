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
@NamedQueries(
        @NamedQuery(name = "getAllPublishers", query = "SELECT p FROM Publisher p")
)
public class Publisher extends AbstractEntity<Long> implements Serializable {

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private List<Book> books;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}