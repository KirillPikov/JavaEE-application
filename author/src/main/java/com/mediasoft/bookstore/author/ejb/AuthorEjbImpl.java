package com.mediasoft.bookstore.author.ejb;

import com.mediasoft.bookstore.author.dao.AbstractAuthorDao;
import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import lombok.AllArgsConstructor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Loggable
@Stateless
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class AuthorEjbImpl implements AuthorEjbLocal, AuthorEjbRemote {

    private AbstractAuthorDao authorDao;

    /**
     * Получение автора по его ID.
     *
     * @param authorId ID автора, которого хотим получить.
     * @return Автора с указанным ID.
     * @throws EntityNotFoundException
     */
    @Loggable
    @Override
    public Author getAuthor(Long authorId) throws EntityNotFoundException {
        return authorDao.findById(authorId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Author with id = " + authorId + " - not found.");
                        }
                );
    }

    /**
     * Получние списка всех авторов.
     *
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Author> getAuthorsPage(Pageable pageable) {
        return authorDao.getAllAuthors(pageable);
    }

    /**
     * Добавление нового автора.
     *
     * @param author новый автор.
     */
    @Override
    public void addAuthor(Author author) throws CreateOrUpdateException {
        if(Objects.nonNull(author)) {
            authorDao.save(author);
        } else {
            throw new CreateOrUpdateException("Can't create author because it is null!");
        }
    }

    /**
     * Изменение состояние автора.
     *
     * @param authorId ID автора.
     * @param author   новое состояние автора.
     * @throws EntityNotFoundException
     */
    @Override
    public void updateAuthor(Long authorId, Author author) throws EntityNotFoundException, CreateOrUpdateException {
        if(Objects.isNull(author)) {
            throw new CreateOrUpdateException("Can't update author because it is null!");
        }
        authorDao.findById(authorId)
                .ifPresentOrElse(
                        repoAuthor -> {
                            List<Book> books = repoAuthor.getBooks();
                            repoAuthor = author;
                            repoAuthor.setId(authorId);
                            repoAuthor.setBooks(books);
                            authorDao.save(repoAuthor);
                        }, () -> {
                            throw new EntityNotFoundException("Author with id = " + author.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление автора по ID.
     *
     * @param authorId ID автора.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteAuthor(Long authorId) throws EntityNotFoundException {
        /* Проверяем существование автора с таким ID */
        if(authorDao.existsById(authorId)) {
            /* Если существует - удаляем */
            authorDao.deleteById(authorId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Author with id = " + authorId + " - not found.");
        }
    }
}