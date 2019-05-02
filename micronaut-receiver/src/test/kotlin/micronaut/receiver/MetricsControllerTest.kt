package micronaut.receiver

import io.micronaut.context.ApplicationContext.run
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EndpointsTest {

    @Nested
    inner class `When hitting the health endpoint`() {

        private val embeddedServer = run(EmbeddedServer::class.java)
        private val client = embeddedServer.applicationContext.createBean(HttpClient::class.java, embeddedServer.url)

        @Test
        fun `and the app is healthy it returns UP`() {
            val response = client.toBlocking().retrieve("/health")
            assertTrue { response.contains("UP") }
        }
    }
}