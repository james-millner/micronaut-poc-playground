package micronaut.poc.metrics

import io.micrometer.prometheus.PrometheusMeterRegistry
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller
class PrometheusMetrics(private val meterRegistry: PrometheusMeterRegistry) {

    @Get("/prometheus/metrics")
    @Produces(MediaType.TEXT_PLAIN)
    fun metrics(): String = meterRegistry.scrape()
}