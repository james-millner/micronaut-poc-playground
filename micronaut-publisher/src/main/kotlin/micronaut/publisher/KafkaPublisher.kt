package micronaut.publisher

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface KafkaPublisher {

    @Topic("my-greetings")
    fun sendGreeting(@KafkaKey greetingId: String, greeting: String)
}
