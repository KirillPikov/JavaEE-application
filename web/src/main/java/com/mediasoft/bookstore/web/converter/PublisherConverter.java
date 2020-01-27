package com.mediasoft.bookstore.web.converter;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbLocal;
import com.mediasoft.bookstore.publisher.entity.Publisher;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.io.Serializable;

@FacesConverter(value = "publisherConverter")
public class PublisherConverter implements Converter, Serializable {

    @Inject
    private PublisherEjbLocal publisherEjbLocal;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return publisherEjbLocal.getPublisher(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Publisher publisher = publisherEjbLocal.getPublisher((Long) value);
        return publisher.getName() + " | " + publisher.getEmail();
    }
}
