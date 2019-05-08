package micronaut.receiver

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import mu.KLogging

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class KafkaListener(val messageService: MessageService) {

    companion object : KLogging()

    @Topic("my-greetings")
    fun receive(@KafkaKey id: String, greeting: String) {
        val message = Message(id, greeting)
        logger.info { "Got Greeting!!! - $message" }
        messageService.saveMessage(message)

        val messages = messageService.getAllMessages()
        logger.info { messages.size }
        logger.info { messages }
    }
}