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

    private final Long idExistingEntity = 15L;

    private final Long idNotExistingEntity = 20L;

    @Before
    public void init() {
        //--------------------------------------< AbstractAuthorDao Mock >---------------------------------------
        //-----------------------< existsById >------------------
        /* Поведение при существующем авторе. */
        Mockito.when(
                authorDao.existsById(
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(true);
        /* Поведение при несуществующем авторе. */
        Mockito.when(
                authorDao.existsById(
                        Mockito.eq(this.idNotExistingEntity)
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
                        Mockito.eq(this.idExistingEntity)
                )
        ).thenReturn(Optional.of(new Author()));
        /* Поведение при несуществующем авторе. */
        Mockito.when(
                authorDao.findById(
                        Mockito.eq(this.idNotExistingEntity)
                )
        ).thenReturn(Optional.empty());
        /* Поведение при передачи null. */
        Mockito.when(
                authorDao.findById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(Optional.empty());
        //----------------------------------------------------------------------------------------------

        /* Создание EJB */
        this.authorEjbLocal = new AuthorEjbImpl(authorDao);
    }

    //====================================================< TESTS >=====================================================

    @Test
    public void twoPlusTwo() {
        Assert.assertEquals(4, 2 + 2);
    }

    //-----------------------------------< getAuthor >--------------------------------------------------
    @Test
    public void getExistingAuthor() {
        Author author = authorEjbLocal.getAuthor(this.idExistingEntity);
        Assert.assertNotNull(author);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistingAuthor() {
        Author author = authorEjbLocal.getAuthor(this.idNotExistingEntity);
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
        authorEjbLocal.updateAuthor(this.idExistingEntity, updatedAuthor);
    }
    /* Обновление автора с существующим id и null телом. */
    @Test(expected = CreateOrUpdateException.class)
    public void updateNullAuthorWithExistingId() {
        Author updatedAuthor = null;
        authorEjbLocal.updateAuthor(this.idExistingEntity, updatedAuthor);
    }
    /* Обновление автора с несуществующим id и nonNull телом. */
    @Test(expected = EntityNotFoundException.class)
    public void updateNonNullAuthorWithNotExistingId() {
        Author updatedAuthor = new Author();
        authorEjbLocal.updateAuthor(this.idNotExistingEntity, updatedAuthor);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< deleteAuthor >------------------------------------------------
    @Test
    public void deleteExistingAuthor() {
        authorEjbLocal.deleteAuthor(this.idExistingEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingAuthor() {
        authorEjbLocal.deleteAuthor(this.idNotExistingEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteAuthorWithNullId() {
        authorEjbLocal.deleteAuthor(null);
    }
    //---------------------------------------------------------------------------------------------------

    //==================================================================================================================
}