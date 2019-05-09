package micronaut.spring.publisher.springpublisher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringPublisherApplication

fun main(args: Array<String>) {
    runApplication<SpringPublisherApplication>(*args)
}
