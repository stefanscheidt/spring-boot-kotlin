package com.rewedigital.talks.customerservice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

const val CUSTOMERS_ENDPOINT = "/api/customers"

@RestController
@RequestMapping(CUSTOMERS_ENDPOINT)
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<CustomerGetResponse> =
        customerRepository
            .findById(id)
            .map { ResponseEntity.ok(it.toGetResponse()) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    fun createCustomer(@RequestBody createRequest: CustomerCreateRequest): ResponseEntity<Unit> =
        customerRepository
            .save(createRequest.toEntity(UUID.randomUUID().toString()))
            .toCreatedResponse()

    private fun CustomerEntity.toCreatedResponse() =
        ResponseEntity
            .created(URI.create("$CUSTOMERS_ENDPOINT/${id}"))
            .build<Unit>()

    private fun CustomerEntity.toGetResponse() =
        CustomerGetResponse(id = id, name = name)

    private fun CustomerCreateRequest.toEntity(id: String) =
        if (name == "Balrok")
            throw UnprocessableEntityExcepction("You can not pass!")
        else
            CustomerEntity(id = id, name = name)

}

data class CustomerCreateRequest(
    val name: String
)

data class CustomerGetResponse(
    val id: String,
    val name: String
)

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class UnprocessableEntityExcepction(message: String) : Exception(message)
