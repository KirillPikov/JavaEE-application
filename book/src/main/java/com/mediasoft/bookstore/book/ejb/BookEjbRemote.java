package com.mediasoft.bookstore.book.ejb;

import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BookEjbRemote {

    /**
     * Получение книги по ID.
     * @param bookId ID книги.
     * @return
     */
    Book getBookById(Long bookId) throws EntityNotFoundException;

    /**
     * Получение страницы всех книг.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooks(Pageable pageable);

    /**
     * Получение всех книг автора по его ID.
     * @param authorId ID автора.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooksByAuthorId(Long authorId, Pageable pageable) throws EntityNotFoundException;

    /**
     * Получение всех книг издателя по его ID.
     * @param publisherId ID издателя.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooksByPublisherId(Long publisherId, Pageable pageable) throws EntityNotFoundException;

    /**
     * Добавление новой книги.
     * @param book новая книга.
     */
    void addBook(Book book);

    /**
     * Изменение состояния киниги.
     * @param bookId ID кинги.
     * @param book новое состояние книги.
     */
    void updateBook(Long bookId, Book book) throws EntityNotFoundException;

    /**
     * Удаление книги по её ID.
     * @param bookId ID книги.
     */
    void deleteBook(Long bookId) throws EntityNotFoundException;
}