package micronaut.publisher

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("micronaut.poc")
            .mainClass(Application.javaClass)
            .start()
    }
}