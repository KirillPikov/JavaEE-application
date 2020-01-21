package com.mediasoft.bookstore.book.ejb;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.book.dao.AbstractBookDao;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbLocal;
import lombok.AllArgsConstructor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Loggable
@Stateless
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class BookEjbImpl implements BookEjbLocal, BookEjbRemote {

    private AbstractBookDao bookDao;

    /**
     * Получение книги по ID.
     *
     * @param bookId ID книги.
     * @return
     */
    @Override
    public Book getBookById(Long bookId) throws EntityNotFoundException {
        return bookDao.findById(bookId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Book with id = " + bookId + " - not found.");
                        }
                );
    }

    /**
     * Получение страницы всех книг.
     *
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooks(Pageable pageable) {
        return bookDao.getAllBooks(pageable);
    }

    /**
     * Получение всех книг автора по его ID.
     *
     * @param authorId ID автора.
     * @param pageable настройка страницы.
     * @return
     */
    @Override //TODO проверка на существование автора с таким ID
    public List<Book> getAllBooksByAuthorId(Long authorId, Pageable pageable) throws EntityNotFoundException {
        return bookDao.getBooksByAuthorId(authorId, pageable);
    }

    /**
     * Получение всех книг издателя по его ID.
     *
     * @param publisherId ID издателя.
     * @param pageable    настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooksByPublisherId(Long publisherId, Pageable pageable) throws EntityNotFoundException {
        return bookDao.getBooksByPublisherId(publisherId, pageable);
    }

    /**
     * Добавление новой книги.
     *
     * @param book новая книга.
     */
    @Override
    public void addBook(Book book) throws CreateOrUpdateException {
        if(Objects.nonNull(book)) {
            bookDao.save(book);
        } else {
            throw new CreateOrUpdateException("Can't create book because it is null!");
        }
    }

    /**
     * Изменение состояния киниги.
     *
     * @param bookId ID кинги.
     * @param book   новое состояние книги.
     */
    @Override
    public void updateBook(Long bookId, Book book) throws EntityNotFoundException, CreateOrUpdateException {
        if(Objects.isNull(book)) {
            throw new CreateOrUpdateException("Can't update book because it is null!");
        }
        /* Выставляю ID книге, так как изначальное он не проинициализирован. */
        book.setId(bookId);
        /* Пробуем найти книгеу по переданному ID */
        bookDao.findById(bookId)
                /* Если получилось найти, меняем её состояние и сохраняем */
                .ifPresentOrElse(
                        repoBook -> {
                            repoBook = book;
                            bookDao.save(repoBook);
                        }, () -> {
                            /* Иначе выбрасываем исключение */
                            throw new EntityNotFoundException("Book with id = " + book.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление книги по её ID.
     *
     * @param bookId ID книги.
     */
    @Override
    public void deleteBook(Long bookId) throws EntityNotFoundException {
        /* Проверяем существование книги с таким ID */
        if(bookDao.existsById(bookId)) {
            /* Если существует - удаляем */
            bookDao.deleteById(bookId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Book with id = " + bookId + " - not found.");
        }
    }
}