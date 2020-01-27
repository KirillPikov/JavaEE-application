package com.mediasoft.bookstore.book.ejb;

import com.mediasoft.bookstore.author.ejb.AuthorEjbLocal;
import com.mediasoft.bookstore.book.dao.AbstractBookDao;
import com.mediasoft.bookstore.book.entity.Book;
import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.ejb.PublisherEjbLocal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookEjbImplTest {

    @Mock
    private AuthorEjbLocal authorEjbLocal;

    @Mock
    private PublisherEjbLocal publisherEjbLocal;

    @Mock
    private AbstractBookDao bookDao;

    @Mock
    private Pageable pageable;

    private BookEjbLocal bookEjbLocal;
    
    private final Long idExistingEntity = 15L;
    
    private final Long idNotExistingEntity = 20L;

    @Before
    public void init() {
        //--------------------------------------< AuthorEjbLocal Mock >---------------------------------------
        //-----------------------< isAuthorExistsById >------------------
        /* Поведение при существующем авторе. */
        Mockito.when(
                authorEjbLocal.isAuthorExistsById(
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(true);
        /* Поведение при несуществующем авторе. */
        Mockito.when(
                authorEjbLocal.isAuthorExistsById(
                        Mockito.eq(this.idNotExistingEntity)
                )
        ).thenReturn(false);
        //-----------------------------------------------------------------------------------------------------

        //--------------------------------------< PublisherEjbLocal Mock >-------------------------------------
        //-----------------------< isPublisherExistsById >------------------
        /* Поведение при существующем издателе. */
        Mockito.when(
                publisherEjbLocal.isPublisherExistsById(
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(true);
        /* Поведение при несуществующем издателе. */
        Mockito.when(
                publisherEjbLocal.isPublisherExistsById(
                        Mockito.eq(this.idNotExistingEntity)
                )
        ).thenReturn(false);
        //-----------------------------------------------------------------------------------------------------

        //--------------------------------------< AbstractBookDao Mock >---------------------------------------
        //-----------------------< existsById >------------------
        /* Поведение при существующей книге. */
        Mockito.when(
                bookDao.existsById(
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(true);
        /* Поведение при несуществующей книге. */
        Mockito.when(
                bookDao.existsById(
                        Mockito.eq(this.idNotExistingEntity)
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
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(Optional.of(new Book()));
        /* Поведение при несуществующей книге. */
        Mockito.when(
                bookDao.findById(
                        Mockito.eq(this.idNotExistingEntity)
                )
        ).thenReturn(Optional.empty());
        /* Поведение при передачи null. */
        Mockito.when(
                bookDao.findById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(Optional.empty());
        //----------------------------------------------------------------------------------------------

        /* Создание EJB */
        bookEjbLocal = new BookEjbImpl(bookDao, authorEjbLocal, publisherEjbLocal);
    }

    //====================================================< TESTS >=====================================================

    @Test
    public void twoPlusTwo() {
        Assert.assertEquals(4, 2 + 2);
    }

    //-----------------------------------< getBookById >-----------------------------
    @Test
    public void getExistingBook() {
        Book book = bookEjbLocal.getBookById(this.idExistingEntity);
        Assert.assertNotNull(book);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistingBook() {
        Book book = bookEjbLocal.getBookById(this.idNotExistingEntity);
        Assert.assertNull(book);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getBookWithNullId() {
        Book book = bookEjbLocal.getBookById(null);
        Assert.assertNull(book);
    }
    //---------------------------------------------------------------------------------

    //-----------------------------------< addBook >-----------------------------------
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
    //----------------------------------------------------------------------------------

    //-----------------------------------< updateBook >---------------------------------
    /* Обновление книги с существующим id и nonNull телом. */
    @Test
    public void updateNonNullBookWithExistingId() {
        Book updatedBook = new Book();
        bookEjbLocal.updateBook(this.idExistingEntity, updatedBook);
    }
    /* Обновление книги с существующим id и null телом. */
    @Test(expected = CreateOrUpdateException.class)
    public void updateNullBookWithExistingId() {
        Book updatedBook = null;
        bookEjbLocal.updateBook(this.idExistingEntity, updatedBook);
    }
    /* Обновление книги с несуществующим id и nonNull телом. */
    @Test(expected = EntityNotFoundException.class)
    public void updateNonNullBookWithNotExistingId() {
        Book updatedBook = new Book();
        bookEjbLocal.updateBook(this.idNotExistingEntity, updatedBook);
    }
    //----------------------------------------------------------------------------------

    //-----------------------------------< deleteBook >---------------------------------
    @Test
    public void deleteExistingBook() {
        bookEjbLocal.deleteBook(this.idExistingEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingBook() {
        bookEjbLocal.deleteBook(this.idNotExistingEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteBookWithNullId() {
        bookEjbLocal.deleteBook(null);
    }
    //----------------------------------------------------------------------------------

    //-----------------------------------< getAllBooksByAuthorId >----------------------
    @Test
    public void getAllBooksByExistingAuthorId() {
        List<Book> books = bookEjbLocal.getAllBooksByAuthorId(this.idExistingEntity, pageable);
        Assert.assertNotNull(books);
    }

    @Test
    public void getAllBooksByExistingAuthorIdAndNullPageable() {
        List<Book> books = bookEjbLocal.getAllBooksByAuthorId(this.idExistingEntity, null);
        Assert.assertNotNull(books);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllBooksByNotExistingAuthorId() {
        bookEjbLocal.getAllBooksByAuthorId(this.idNotExistingEntity, pageable);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllBooksByNullAuthorId() {
        bookEjbLocal.getAllBooksByAuthorId(null, pageable);
    }
    //----------------------------------------------------------------------------------

    //-----------------------------------< getAllBooksByPublisherId >-------------------
    @Test
    public void getAllBooksByExistingPublisherId() {
        List<Book> books = bookEjbLocal.getAllBooksByPublisherId(this.idExistingEntity, pageable);
        Assert.assertNotNull(books);
    }

    @Test
    public void getAllBooksByExistingPublisherIdAndNullPageable() {
        List<Book> books = bookEjbLocal.getAllBooksByPublisherId(this.idExistingEntity, null);
        Assert.assertNotNull(books);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllBooksByNotExistingPublisherId() {
        bookEjbLocal.getAllBooksByPublisherId(this.idNotExistingEntity, pageable);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAllBooksByNullPublisherId() {
        bookEjbLocal.getAllBooksByPublisherId(null, pageable);
    }
    //----------------------------------------------------------------------------------

    //==================================================================================================================
}