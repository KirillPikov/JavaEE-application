package com.mediasoft.bookstore.book.ejb;

import com.mediasoft.bookstore.author.ejb.AuthorEjbRemote;
import com.mediasoft.bookstore.book.dao.AbstractBookDao;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BookEjbImpl implements BookEjbLocal, BookEjbRemote {

    @Inject
    private AbstractBookDao bookDao;

    @EJB
    private AuthorEjbRemote authorEjbRemote;

    @EJB
    private PublisherEjbRemote publisherEjbRemote;

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
    @Override //TODO Criteria or JPQL
    public List<Book> getAllBooks(Pageable pageable) {
        return null;
    }

    /**
     * Получение всех книг автора по его ID.
     *
     * @param authorId ID автора.
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooksByAuthorId(Long authorId, Pageable pageable) throws EntityNotFoundException {
        return authorEjbRemote.getAuthor(authorId).getBooks();
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
        return publisherEjbRemote.getPublisher(publisherId).getBooks();
    }

    /**
     * Добавление новой книги.
     *
     * @param book новая книга.
     */
    @Override
    public void addBook(Book book) {
        bookDao.save(book);
    }

    /**
     * Изменение состояния киниги.
     *
     * @param bookId ID кинги.
     * @param book   новое состояние книги.
     */
    @Override
    public void updateBook(Long bookId, Book book) throws EntityNotFoundException {
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