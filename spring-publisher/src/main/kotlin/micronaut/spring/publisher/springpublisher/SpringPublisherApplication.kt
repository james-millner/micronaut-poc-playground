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
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable

@SpringBootApplication
class SpringPublisherApplication

fun main(args: Array<String>) {
    runApplication<SpringPublisherApplication>(*args)
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

@Controller
class Hello() {

    @GetMapping("/hello")
    fun helloThere() = "Hello"
}
