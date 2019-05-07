package micronaut.receiver

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Context
import mu.KLogging
import javax.inject.Inject

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class ProductListener(private val messagedb: MessageRepository) {


    companion object : KLogging()

    @Topic("my-greetings")
    fun receive(@KafkaKey id: String, greeting: String) {
        logger.info { "Got Greeting!!! - $id by $greeting" }
        messagedb.save(id, greeting)
        logger.info { "Successfully saved." }
        val greetingObj = messagedb.findById(id)
        logger.info { greetingObj.toString() }
    }
}