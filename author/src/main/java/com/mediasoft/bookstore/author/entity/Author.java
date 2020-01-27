package com.mediasoft.bookstore.author.entity;

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
        @NamedQuery(name = "getAllAuthors", query = "SELECT a FROM Author a")
)
public class Author extends AbstractEntity<Long> implements Serializable {

    private String name;

    private String address;

    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
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