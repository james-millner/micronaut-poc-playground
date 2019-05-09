package micronaut.spring.publisher.springpublisher

import mu.KLogging
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.apache.kafka.clients.producer.internals.Sender
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable

@SpringBootApplication
class SpringPublisherApplication

fun main(args: Array<String>) {
    runApplication<SpringPublisherApplication>(*args)
}

@Configuration
class SenderConfig {

    @Value("\${kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    @Bean
    fun producerConfigs(): Map<String, Serializable?> {
        return mapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
                )
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }
}

@RestController
class SpringPublisher(val kafkaTemplate: KafkaTemplate<String, String>) {

    companion object : KLogging()

    @PostMapping("/kafka/send")
    fun send(@RequestParam("greeting") greeting: String) {
        kafkaTemplate.send("my-greetings-spring", greeting)
        logger.info { "Sent $greeting to Kafka" }
    }
}
