package com.mediasoft.bookstore.publisher.ejb;

import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.publisher.dao.AbstractPublisherDao;
import com.mediasoft.bookstore.publisher.entity.Publisher;
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
public class PublisherEjbImplTest {

    @Mock
    private AbstractPublisherDao publisherDao;

    private PublisherEjbLocal publisherEjbLocal;

    private final Long idExistingPublisher = 15L;

    private final Long idNotExistingPublisher = 20L;

    @Before
    public void init() {
        //-----------------------< existsById >------------------
        /* Поведение при существующем издателе. */
        Mockito.when(
                publisherDao.existsById(
                        Mockito.eq(this.idExistingPublisher)
                )
        ).thenReturn(true);
        /* Поведение при несуществующем издателе. */
        Mockito.when(
                publisherDao.existsById(
                        Mockito.eq(this.idNotExistingPublisher)
                )
        ).thenReturn(false);
        /* Поведение при передачи null. */
        Mockito.when(
                publisherDao.existsById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(false);

        //-----------------------< findById >------------------
        /* Поведение при существующем издателе. */
        Mockito.when(
                publisherDao.findById(
                        Mockito.eq(this.idExistingPublisher)
                )
        ).thenReturn(Optional.of(new Publisher()));
        /* Поведение при несуществующем издателе. */
        Mockito.when(
                publisherDao.findById(
                        Mockito.eq(this.idNotExistingPublisher)
                )
        ).thenReturn(Optional.empty());
        /* Поведение при передачи null. */
        Mockito.when(
                publisherDao.findById(
                        Mockito.argThat(Objects::isNull)
                )
        ).thenReturn(Optional.empty());

        /* Создание EJB */
        this.publisherEjbLocal = new PublisherEjbImpl(publisherDao);
    }

    @Test
    public void twoPlusTwo() {
        Assert.assertEquals(4, 2 + 2);
    }

    //-----------------------------------< getPublisher >--------------------------------------------------
    @Test
    public void getExistingPublisher() {
        Publisher publisher = publisherEjbLocal.getPublisher(this.idExistingPublisher);
        Assert.assertNotNull(publisher);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistingPublisher() {
        Publisher publisher = publisherEjbLocal.getPublisher(this.idNotExistingPublisher);
        Assert.assertNull(publisher);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getPublisherWithNullId() {
        Publisher publisher = publisherEjbLocal.getPublisher(null);
        Assert.assertNull(publisher);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< addPublisher >---------------------------------------------------
    @Test
    public void addNonNullPublisher() {
        Publisher newPublisher = new Publisher();
        publisherEjbLocal.addPublisher(newPublisher);
    }

    @Test(expected = CreateOrUpdateException.class)
    public void addNullPublisher() {
        Publisher newPublisher = null;
        publisherEjbLocal.addPublisher(newPublisher);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< updatePublisher >------------------------------------------------
    /* Обновление издателя с существующим id и nonNull телом. */
    @Test
    public void updateNonNullPublisherWithExistingId() {
        Publisher updatedPublisher = new Publisher();
        publisherEjbLocal.updatePublisher(this.idExistingPublisher, updatedPublisher);
    }
    /* Обновление издателя с существующим id и null телом. */
    @Test(expected = CreateOrUpdateException.class)
    public void updateNullPublisherWithExistingId() {
        Publisher updatedPublisher = null;
        publisherEjbLocal.updatePublisher(this.idExistingPublisher, updatedPublisher);
    }
    /* Обновление издателя с несуществующим id и nonNull телом. */
    @Test(expected = EntityNotFoundException.class)
    public void updateNonNullPublisherWithNotExistingId() {
        Publisher updatedPublisher = new Publisher();
        publisherEjbLocal.updatePublisher(this.idNotExistingPublisher, updatedPublisher);
    }
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------< deletePublisher >------------------------------------------------
    @Test
    public void deleteExistingPublisher() {
        publisherEjbLocal.deletePublisher(this.idExistingPublisher);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistingPublisher() {
        publisherEjbLocal.deletePublisher(this.idNotExistingPublisher);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deletePublisherWithNullId() {
        publisherEjbLocal.deletePublisher(null);
    }
    //---------------------------------------------------------------------------------------------------
}