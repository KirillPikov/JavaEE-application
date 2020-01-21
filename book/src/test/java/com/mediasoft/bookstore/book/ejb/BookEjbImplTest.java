package com.mediasoft.bookstore.book.ejb;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.book.dao.AbstractBookDao;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbLocal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Objects;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookEjbImplTest {

    @Mock
    AuthorEjbLocal authorEjbLocal;

    @Mock
    PublisherEjbLocal publisherEjbLocal;

    @Mock
    private AbstractBookDao bookDao;

    private BookEjbLocal bookEjbLocal;
    
    private final Long idExistingBook = 15L;
    
    private final Long idNotExistingBook = 20L;

    @Before
    public void init() {
        //-----------------------< existsById >------------------
        /* Поведение при существующей книге. */
        Mockito.when(
                bookDao.existsById(
                        Mockito.eq(this.idExistingBook)
                )
        ).thenReturn(true);
        /* Поведение при несуществующей книге. */
        Mockito.when(
                bookDao.existsById(
                        Mockito.eq(this.idNotExistingBook)
                )
        ).thenReturn(false);
        /* Поведение при передачи null. */
        Mockito.when(
                bookDao.existsById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(false);

        //-----------------------< findById >------------------
        /* Поведение при существующей книге. */
        Mockito.when(
                bookDao.findById(
                        Mockito.eq(this.idExistingBook)
                )
        ).thenReturn(Optional.of(new Book()));
        /* Поведение при несуществующей книге. */
        Mockito.when(
                bookDao.findById(
                        Mockito.eq(this.idNotExistingBook)
                )
        ).thenReturn(Optional.empty());
        /* Поведение при передачи null. */
        Mockito.when(
                bookDao.findById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(Optional.empty());

        /* Создание EJB */
        bookEjbLocal = new BookEjbImpl(bookDao);
    }

    @Test
    public void twoPlusTwo() {
        Assert.assertEquals(4, 2 + 2);
    }

    //-----------------------------------< getBookById >----------------------------------------------
    @Test
    public void getExistingBook() {
        Book book = bookEjbLocal.getBookById(this.idExistingBook);
        Assert.assertNotNull(book);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistingBook() {
        Book book = bookEjbLocal.getBookById(this.idNotExistingBook);
        Assert.assertNull(book);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getBookWithNullId() {
        Book book = bookEjbLocal.getBookById(null);
        Assert.assertNull(book);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< addBook >---------------------------------------------------
    @Test
    public void addNonNullBook() {
        Book newBook = new Book();
        bookEjbLocal.addBook(newBook);
    }

    @Test(expected = CreateOrUpdateException.class)
    public void addNullBook() {
        Book newBook = null;
        bookEjbLocal.addBook(newBook);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< updateBook >------------------------------------------------
    /* Обновление книги с существующим id и nonNull телом. */
    @Test
    public void updateNonNullBookWithExistingId() {
        Book updatedBook = new Book();
        bookEjbLocal.updateBook(this.idExistingBook, updatedBook);
    }
    /* Обновление книги с существующим id и null телом. */
    @Test(expected = CreateOrUpdateException.class)
    public void updateNullBookWithExistingId() {
        Book updatedBook = null;
        bookEjbLocal.updateBook(this.idExistingBook, updatedBook);
    }
    /* Обновление книги с несуществующим id и nonNull телом. */
    @Test(expected = EntityNotFoundException.class)
    public void updateNonNullBookWithNotExistingId() {
        Book updatedBook = new Book();
        bookEjbLocal.updateBook(this.idNotExistingBook, updatedBook);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< deleteBook >------------------------------------------------
    @Test
    public void deleteExistingBook() {
        bookEjbLocal.deleteBook(this.idExistingBook);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingBook() {
        bookEjbLocal.deleteBook(this.idNotExistingBook);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteBookWithNullId() {
        bookEjbLocal.deleteBook(null);
    }
    //---------------------------------------------------------------------------------------------------
}