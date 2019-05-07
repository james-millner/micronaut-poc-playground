package micronaut.receiver

import com.sun.istack.NotNull

interface MessageRepository {
    fun findById(@NotNull id: String): Message

    fun save(id: String, greeting: String): Message
}