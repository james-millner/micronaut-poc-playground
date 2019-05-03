package micronaut.receiver

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("micronaut.receiver")
            .mainClass(Application.javaClass)
            .start()
    }
}