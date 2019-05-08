package micronaut.receiver

import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional

@Singleton
open class MessageRepositoryImp(
    @CurrentSession @PersistenceContext
    private var entityManager: EntityManager) {

    open fun save(id: String, greeting: String): Message {
        val message = Message(id, greeting)

        entityManager.persist(message)
        return message
    }


    open fun findById( id: String): Message {
        return entityManager.find(Message::class.java, id)
    }

}

