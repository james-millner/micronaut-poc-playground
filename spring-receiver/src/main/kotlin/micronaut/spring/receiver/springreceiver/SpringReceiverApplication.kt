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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@SpringBootApplication
class SpringReceiverApplication

fun main(args: Array<String>) {
    runApplication<SpringReceiverApplication>(*args).registerShutdownHook()
}

@EnableKafka
@Configuration
class ConsumerConfig {

    @Value("\${kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    fun consumerFactory(groupId: String): ConsumerFactory<String, String> {
        val props = mapOf(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG to StringSerializer::class.java,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {

        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory("spring")
        return factory
    }
}

@Component
class KafkaListener(
        private val service: GreetingService
) {

    companion object : KLogging()

    @KafkaListener(topics = ["my-greetings-spring"], groupId = "test-consumer-group")
    fun listen(@Payload greetingMessage: String) {
        logger.info { "Spring Greeting of: \"$greetingMessage\" received" }

        val  greetingId = service.returnGreetingId(greetingMessage).id
        logger.info { "Saved into mySQL database, greeting of id: $greetingId." }

        val getGreetingWithId = service.getGreetingById(greetingId)
        logger.info { "Greeting message with id: $greetingId is \"${getGreetingWithId.greeting}\". " }

    }

}



