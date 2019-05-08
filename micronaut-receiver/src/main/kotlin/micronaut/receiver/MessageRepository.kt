package micronaut.receiver

interface MessageRepository {

    fun findById(id: String): Message
    fun save(id: String, greeting: String): Message
}