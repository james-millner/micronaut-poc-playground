package micronaut.receiver

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import mu.KLogging

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class ProductListener {

    companion object : KLogging()

    @Topic("my-greetings")
    fun receive(@KafkaKey id: String, greeting: String) {
        logger.info { "Got Greeting!!! - $id by $greeting" }
    }
}