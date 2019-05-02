package micronaut.publisher

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("micronaut.publisher")
            .mainClass(Application.javaClass)
            .start()
    }
}