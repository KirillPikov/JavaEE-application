package com.mediasoft.bookstore.publisher.dao;

import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.entity.Publisher;

import java.util.List;

public class PublisherDao extends AbstractPublisherDao {

    @Override
    public List<Publisher> getAllPublishers(Pageable pageable) {
        return this.namedQuery("getAllPublishers")
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getSize())
                .getResultList();
    }
}