package micronaut.publisher

import io.micronaut.context.annotation.Requires
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import javax.inject.Inject

@Controller(value = "/kafka")
class PublisherController(@Inject val client: KafkaPublisher) {

    @Post("/send")
    @Produces(MediaType.TEXT_PLAIN)
    fun metrics(@QueryValue id: String, @QueryValue greeting: String): Boolean {
        client.sendGreeting(id, greeting)
        return true
    }
}
