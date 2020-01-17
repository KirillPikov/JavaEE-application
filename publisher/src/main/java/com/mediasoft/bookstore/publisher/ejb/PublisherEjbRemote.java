package com.mediasoft.bookstore.publisher.ejb;

import com.mediasoft.bookstore.common.exception.EntityNotFoundException;
import com.mediasoft.bookstore.common.pageable.Pageable;
import com.mediasoft.bookstore.publisher.entity.Publisher;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PublisherEjbRemote {

    /**
     * Получение издателя по его ID.
     * @param publisherId ID издателя, которого хотим получить.
     * @return издателя с указанным ID.
     * @throws EntityNotFoundException
     */
    Publisher getPublisher(Long publisherId) throws EntityNotFoundException;

    /**
     * Получние списка всех издателей.
     * @return
     */
    List<Publisher> getPublishersPage(Pageable pageable);

    /**
     * Добавление нового издателя.
     * @param publisher новый издатель.
     */
    void addPublisher(Publisher publisher);

    /**
     * Изменение состояние издателя.
     * @param publisherId ID издателя.
     * @param publisher новое состояние издателя.
     * @throws EntityNotFoundException
     */
    void updatePublisher(Long publisherId, Publisher publisher) throws EntityNotFoundException;

    /**
     * Удаление издателя по ID.
     * @param publisherId ID издателя.
     * @throws EntityNotFoundException
     */
    void deletePublisher(Long publisherId) throws EntityNotFoundException;
}
