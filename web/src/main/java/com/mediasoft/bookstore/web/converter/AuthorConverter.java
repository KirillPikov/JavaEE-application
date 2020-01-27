package com.mediasoft.bookstore.web.converter;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.author.entity.Author;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.io.Serializable;

@FacesConverter(value = "authorConverter")
public class AuthorConverter implements Converter, Serializable {

    private AuthorEjbLocal authorEjbLocal;

    @Inject
    public AuthorConverter(AuthorEjbLocal authorEjbLocal) {
        this.authorEjbLocal = authorEjbLocal;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return authorEjbLocal.getAuthor(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Author author = authorEjbLocal.getAuthor((Long) value);
        return author.getName() + " | " + author.getEmail();
    }
}