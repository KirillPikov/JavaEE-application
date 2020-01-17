package com.mediasoft.bookstore.author.ejb;

import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AuthorEjbRemote {

    /**
     * Получение автора по его ID.
     * @param authorId ID автора, которого хотим получить.
     * @return Автора с указанным ID.
     */
    Author getAuthor(Long authorId) throws EntityNotFoundException;

    /**
     * Получние списка всех авторов.
     * @param pageable настройка страницы.
     * @return
     */
    List<Author> getAuthorsPage(Pageable pageable);

    /**
     * Добавление нового автора.
     * @param author новый автор.
     */
    void addAuthor(Author author);

    /**
     * Изменение состояние автора.
     * @param authorId ID автора.
     * @param author новое состояние автора.
     */
    void updateAuthor(Long authorId, Author author) throws EntityNotFoundException;

    /**
     * Удаление автора по ID.
     * @param authorId ID автора.
     */
    void deleteAuthor(Long authorId) throws EntityNotFoundException;
}
