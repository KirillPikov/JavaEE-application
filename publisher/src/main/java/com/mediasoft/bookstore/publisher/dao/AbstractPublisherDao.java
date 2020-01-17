package com.mediasoft.bookstore.publisher.dao;

import com.mediasoft.bookstore.common.dao.AbstractDao;
import com.mediasoft.bookstore.publisher.entity.Publisher;

public abstract class AbstractPublisherDao extends AbstractDao<Publisher, Long> {

    public AbstractPublisherDao() {
        super(Publisher.class);
    }
}