package com.rewedigital.talks.customerservice

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import java.util.UUID.randomUUID

const val CUSTOMERS_ENDPOINT = "/api/customers"

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(CUSTOMERS_ENDPOINT)
class CustomerController(private val customerRepository: CustomerRepository) {

    @PostMapping
    fun createCustomer(@RequestBody request: CreateCustomerRequest): ResponseEntity<Unit> {
        val uuid = randomUUID()
        customerRepository.save(Customer(id = uuid, name = request.name))
        return ResponseEntity
            .created(URI.create("$CUSTOMERS_ENDPOINT/$uuid"))
            .build()
    }

    @GetMapping("{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<Customer> {
        val uuid = id.toUuidOrNull() ?: return ResponseEntity.badRequest().body(null)
        val customer = customerRepository.findById(uuid)
        return if (customer != null)
            ResponseEntity.ok(customer)
        else
            ResponseEntity.notFound().build()
    }

    private fun String.toUuidOrNull() = try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        logger.info { "cannot parse ID string $this" }
        null
    }

}

data class CreateCustomerRequest(
    val name: String
)
