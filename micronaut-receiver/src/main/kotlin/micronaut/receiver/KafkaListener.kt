package micronaut.receiver

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import mu.KLogging

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class KafkaListener(private val greetingService: GreetingService) {

    companion object : KLogging()

    @Topic("my-greetings")
    fun receive(@KafkaKey id: String, greeting: String) {
        logger.info { "Got Greeting of $greeting" }

        val id = greetingService.save(greeting)
        logger.info { "Saved! $id" }

        val greetings = greetingService.getAllGreetings()
        logger.info { "I now have: ${greetings.size}" }

        greetings.forEach { logger.info { it } }
    }
}