package micronaut.receiver

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Body
import mu.KLogging

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class KafkaListener(private val greetingService: GreetingService) {

    companion object : KLogging()

    @Topic("my-greetings")
    fun receive(@Body greeting: String) {
        logger.info { "Got Greeting of $greeting" }

        val greetingId = greetingService.save(greeting)
        logger.info { "Saved! $greetingId" }

        val greetings = greetingService.getAllGreetings()
        logger.info { "I now have: ${greetings.size}" }

        greetings.forEach { logger.info { it } }
    }
}
