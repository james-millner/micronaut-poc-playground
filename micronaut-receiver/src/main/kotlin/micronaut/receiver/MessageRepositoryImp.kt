package micronaut.receiver

import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional
import javax.validation.constraints.NotNull

@Singleton
open class MessageRepositoryImp(@CurrentSession @PersistenceContext private var entityManager: EntityManager): MessageRepository {

    override fun save(id: String, greeting: String): Message {
        val message = Message(id, greeting)

        entityManager.persist(message)
        return message
    }

    @Transactional(readOnly = true)
    override fun findById(@NotNull id: String): Message {
        return entityManager.find(Message::class.java, id)
    }

}

