package com.mediasoft.bookstore.publisher.ejb;

import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.dao.AbstractPublisherDao;
import com.mediasoft.bookstore.publisher.entity.Publisher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PublisherEjbImpl implements PublisherEjbLocal, PublisherEjbRemote {

    @Inject
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
    @Override   //TODO Criteria or JPQL
    public List<Publisher> getPublishersPage(Pageable pageable) {
        return null;
    }

    /**
     * Добавление нового издателя.
     *
     * @param publisher новый издатель.
     */
    @Override
    public void addPublisher(Publisher publisher) {
        publisherDao.save(publisher);
    }

    /**
     * Изменение состояние издателя.
     *
     * @param publisherId ID издателя.
     * @param publisher   новое состояние издателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException {
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
}