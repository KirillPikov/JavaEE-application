package com.mediasoft.bookstore.web.controller;

import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.common.pageable.Sort;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbLocal;
import com.mediasoft.bookstore.publisher.entity.Publisher;
import com.mediasoft.bookstore.web.config.PageSettings;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Loggable
@RequestScoped
public class PublisherController{

    @Inject
    private PublisherEjbLocal publisherEjbLocal;

    private Publisher publisher;

    private Integer page;

    @PostConstruct
    public void init() {
        publisher = new Publisher();
        page = 0;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPage(Integer page) {
        this.page = page < 0 ? 0 : page;
    }

    public Integer getPage() {
        return page;
    }

    public Publisher findPublisherById() {
        publisher = publisherEjbLocal.getPublisher(publisher.getId());
        return publisher;
    }

    public List<Publisher> getPublishersPage() {
        Integer size = PageSettings.TABLE_ROW_COUNT_SIZE;
        return publisherEjbLocal.getPublishersPage(new Pageable(size * page, size, Sort.ASC));
    }
}
