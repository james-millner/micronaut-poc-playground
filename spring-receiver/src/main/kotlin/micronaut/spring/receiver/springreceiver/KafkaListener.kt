package micronaut.spring.receiver.springreceiver

import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class KafkaListener(private val service: GreetingService) {

    companion object : KLogging()

    @KafkaListener(topics = ["my-greetings-spring"], groupId = "test-consumer-group")
    fun listen(@Payload greetingMessage: String) {

        logger.info { "Spring Greeting of: \"$greetingMessage\" received" }

        val  greetingId = service.returnGreetingId(greetingMessage).id
        logger.info { "Saved into mySQL database, greeting of id: $greetingId." }

        val getGreetingFromId = service.getGreetingById(greetingId)
        logger.info { "Greeting message with id: $greetingId is \"${getGreetingFromId.greeting}\". " }

    }

}