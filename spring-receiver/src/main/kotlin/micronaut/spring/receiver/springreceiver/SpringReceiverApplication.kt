package micronaut.spring.receiver.springreceiver

import mu.KLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@EnableKafka
@SpringBootApplication
class SpringReceiverApplication

fun main(args: Array<String>) {
    runApplication<SpringReceiverApplication>(*args).registerShutdownHook()
}

@Component
class KafkaListener {

    companion object : KLogging()

    @KafkaListener(topics = ["my-greetings-spring"])
    fun listen(@Payload greeting: String) {
        logger.info { "Spring Greeting of: \"$greeting\" received" }
    }
}
