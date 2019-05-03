package micronaut.publisher

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Requires

@Requires(env = ["producation"])
@KafkaClient
interface KafkaPublisher {

    @Topic("my-greetings")
    fun sendGreeting(@KafkaKey greetingId: String, greeting: String)
}
