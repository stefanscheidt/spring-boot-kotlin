package com.rewedigital.talks.customerservice

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router
import java.net.URI
import java.util.*
import java.util.UUID.randomUUID

const val CUSTOMERS_ENDPOINT = "/api/customers"

private val logger = KotlinLogging.logger {}

// @RestController
// @RequestMapping(CUSTOMERS_ENDPOINT)
class CustomerController(private val customerRepository: CustomerRepository) {

    @PostMapping
    fun createCustomer(@RequestBody command: CreateCustomerCommand): ResponseEntity<Unit> {
        val uuid = randomUUID()
        customerRepository.save(Customer(id = uuid, name = command.name))
        return ResponseEntity
            .created(URI.create("$CUSTOMERS_ENDPOINT/$uuid"))
            .build()
    }

    @GetMapping("{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<Customer> {
        val uuid = id.toUuidOrNull()
            ?: return ResponseEntity.badRequest().body(null)
        val customer = customerRepository.findById(uuid)
        return if (customer != null)
            ResponseEntity.ok(customer)
        else
            ResponseEntity.notFound().build()
    }

}

@Configuration
class CustomerRouterConfiguration {
    @Bean
    fun customerRouter(customerAPI: CustomerAPI) = router {
        CUSTOMERS_ENDPOINT.nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/{id}", customerAPI::getCustomer)
                POST("/", customerAPI::createCustomer)
            }
        }
    }
}

@Component
class CustomerAPI(private val customerRepository: CustomerRepository) {

    fun getCustomer(request: ServerRequest): ServerResponse {
        val uuid = request.pathVariable("id").toUuidOrNull()
            ?: return ServerResponse.badRequest().build()
        val customer = customerRepository.findById(uuid)
        return if (customer != null)
            ServerResponse.ok().body(customer)
        else
            ServerResponse.notFound().build()
    }

    fun createCustomer(request: ServerRequest): ServerResponse {
        val body = request.body<CreateCustomerCommand>()
        val uuid = randomUUID()
        customerRepository.save(Customer(id = uuid, name = body.name))
        return ServerResponse
            .created(URI.create("$CUSTOMERS_ENDPOINT/$uuid"))
            .build()
    }

}

data class CreateCustomerCommand(
    val name: String
)

private fun String.toUuidOrNull() = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    logger.info { "cannot parse ID string $this" }
    null
}
