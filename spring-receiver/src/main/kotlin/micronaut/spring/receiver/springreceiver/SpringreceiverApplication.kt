package micronaut.spring.receiver.springreceiver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringreceiverApplication

fun main(args: Array<String>) {
    runApplication<SpringreceiverApplication>(*args)
}
