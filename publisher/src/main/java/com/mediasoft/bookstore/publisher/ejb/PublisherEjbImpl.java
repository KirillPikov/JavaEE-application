package com.mediasoft.bookstore.publisher.ejb;

import com.mediasoft.bookstore.common.exception.CreateOrUpdateException;
import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.loggable.Loggable;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.dao.AbstractPublisherDao;
import com.mediasoft.bookstore.publisher.entity.Publisher;
import lombok.AllArgsConstructor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Loggable
@Stateless
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class PublisherEjbImpl implements PublisherEjbLocal, PublisherEjbRemote {

    private AbstractPublisherDao publisherDao;

    /**
     * Получение издателя по его ID.
     *
     * @param publisherId ID издателя, которого хотим получить.
     * @return издателя с указанным ID.
     * @throws EntityNotFoundException
     */
    @Override
    public Publisher getPublisher(Long publisherId) throws EntityNotFoundException {
        return publisherDao.findById(publisherId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Publisher with id = " + publisherId + " - not found.");
                        }
                );
    }

    /**
     * Получние списка всех издателей.
     *
     * @param pageable
     * @return
     */
    @Override
    public List<Publisher> getPublishersPage(Pageable pageable) {
        if(Objects.isNull(pageable)) {
            pageable = new Pageable();
        }
        return publisherDao.getAllPublishers(pageable);
    }

    /**
     * Добавление нового издателя.
     *
     * @param publisher новый издатель.
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void addPublisher(Publisher publisher) {
        if(Objects.nonNull(publisher)) {
            publisherDao.save(publisher);
        } else {
            throw new CreateOrUpdateException("Can't update publisher because it is null!");
        }
    }

    /**
     * Изменение состояние издателя.
     *
     * @param publisherId ID издателя.
     * @param publisher   новое состояние издателя.
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException, CreateOrUpdateException {
        if(Objects.isNull(publisher)) {
            throw new CreateOrUpdateException("Can't update publisher because it is null!");
        }
        publisher.setId(publisherId);
        publisherDao.findById(publisherId)
                .ifPresentOrElse(
                        repoPublisher -> {
                            repoPublisher = publisher;
                            publisherDao.save(repoPublisher);
                        }, () -> {
                            throw new EntityNotFoundException("Publisher with id = " + publisher.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление издателя по ID.
     *
     * @param publisherId ID издателя.
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deletePublisher(Long publisherId) throws EntityNotFoundException {
        /* Проверяем существование издателя с таким ID */
        if(publisherDao.existsById(publisherId)) {
            /* Если существует - удаляем */
            publisherDao.deleteById(publisherId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Publisher with id = " + publisherId + " - not found.");
        }
    }

    /**
     * Првоеряет существование издателя с таким Id.
     *
     * @param publisherId
     * @return
     */
    @Override
    public Boolean isPublisherExistsById(Long publisherId) {
        return publisherDao.existsById(publisherId);
    }
}