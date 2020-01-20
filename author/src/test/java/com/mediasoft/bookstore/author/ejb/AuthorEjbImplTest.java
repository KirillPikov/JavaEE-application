package com.mediasoft.bookstore.author.ejb;

import com.mediasoft.bookstore.author.dao.AbstractAuthorDao;
import com.mediasoft.bookstore.author.entity.Author;
import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
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
public class AuthorEjbImplTest {

    @Mock
    private AbstractAuthorDao authorDao;

    private AuthorEjbLocal authorEjbLocal;

    private final Long idExistingAuthor = 15L;

    private final Long idNotExistingAuthor = 20L;

    @Before
    public void init() {
        //-----------------------< existsById >------------------
        /* Поведение при существующем авторе. */
        Mockito.when(
                authorDao.existsById(
                        Mockito.eq(this.idExistingAuthor)
                )
        ).thenReturn(true);
        /* Поведение при несуществующем авторе. */
        Mockito.when(
                authorDao.existsById(
                        Mockito.eq(this.idNotExistingAuthor)
                )
        ).thenReturn(false);
        /* Поведение при передачи null. */
        Mockito.when(
                authorDao.existsById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(false);

        //-----------------------< findById >------------------
        /* Поведение при существующем авторе. */
        Mockito.when(
                authorDao.findById(
                        Mockito.eq(this.idExistingAuthor)
                )
        ).thenReturn(Optional.of(new Author()));
        /* Поведение при несуществующем авторе. */
        Mockito.when(
                authorDao.findById(
                        Mockito.eq(this.idNotExistingAuthor)
                )
        ).thenReturn(Optional.empty());
        /* Поведение при передачи null. */
        Mockito.when(
                authorDao.findById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(Optional.empty());

        /* Создание EJB */
        this.authorEjbLocal = new AuthorEjbImpl(authorDao);
    }

    @Test
    public void twoPlusTwo() {
        Assert.assertEquals(4, 2 + 2);
    }

    //-----------------------------------< getAuthor >--------------------------------------------------
    @Test
    public void getExistingAuthor() {
        Author author = authorEjbLocal.getAuthor(this.idExistingAuthor);
        Assert.assertNotNull(author);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistingAuthor() {
        Author author = authorEjbLocal.getAuthor(this.idNotExistingAuthor);
        Assert.assertNull(author);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getAuthorWithNullId() {
        Author author = authorEjbLocal.getAuthor(null);
        Assert.assertNull(author);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< addAuthor >---------------------------------------------------
    @Test
    public void addNonNullAuthor() {
        Author newAuthor = new Author();
        authorEjbLocal.addAuthor(newAuthor);
    }

    @Test(expected = CreateOrUpdateException.class)
    public void addNullAuthor() {
        Author newAuthor = null;
        authorEjbLocal.addAuthor(newAuthor);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< updateAuthor >------------------------------------------------
    /* Обновление автора с существующим id и nonNull телом. */
    @Test
    public void updateNonNullAuthorWithExistingId() {
        Author updatedAuthor = new Author();
        authorEjbLocal.updateAuthor(this.idExistingAuthor, updatedAuthor);
    }
    /* Обновление автора с существующим id и null телом. */
    @Test(expected = CreateOrUpdateException.class)
    public void updateNullAuthorWithExistingId() {
        Author updatedAuthor = null;
        authorEjbLocal.updateAuthor(this.idExistingAuthor, updatedAuthor);
    }
    /* Обновление автора с несуществующим id и nonNull телом. */
    @Test(expected = EntityNotFoundException.class)
    public void updateNonNullAuthorWithNotExistingId() {
        Author updatedAuthor = new Author();
        authorEjbLocal.updateAuthor(this.idNotExistingAuthor, updatedAuthor);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< deleteAuthor >------------------------------------------------
    @Test
    public void deleteExistingAuthor() {
        authorEjbLocal.deleteAuthor(this.idExistingAuthor);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingAuthor() {
        authorEjbLocal.deleteAuthor(this.idNotExistingAuthor);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteAuthorWithNullId() {
        authorEjbLocal.deleteAuthor(null);
    }
    //---------------------------------------------------------------------------------------------------
}