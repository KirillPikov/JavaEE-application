package com.mediasoft.bookstore.publisher.dao;

import com.mediasoft.bookstore.common.dao.AbstractDao;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.entity.Publisher;

import java.util.List;

public abstract class AbstractPublisherDao extends AbstractDao<Publisher, Long> {

    public AbstractPublisherDao() {
        super(Publisher.class);
    }

    public abstract List<Publisher> getAllPublishers(Pageable pageable);
}