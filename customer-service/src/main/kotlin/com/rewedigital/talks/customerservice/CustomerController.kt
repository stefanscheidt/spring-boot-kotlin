package com.rewedigital.talks.customerservice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<CustomerGetResponse> =
        customerRepository
            .findById(id)
            .map { ResponseEntity.ok(it.toGetResponse()) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping("/customers")
    fun createCustomer(@RequestBody createRequest: CustomerCreateRequest): ResponseEntity<Unit> =
        customerRepository
            .save(createRequest.toEntity())
            .toCreatedResponse()

    private fun CustomerEntity.toCreatedResponse() =
        ResponseEntity
            .created(URI.create("/customers/${id!!}"))
            .build<Unit>()

    private fun CustomerEntity.toGetResponse() =
        CustomerGetResponse(id = id!!, name = name)

    private fun CustomerCreateRequest.toEntity() =
        if (name == "Balrok")
            throw UnprocessableEntityExcepction("You can not pass!")
        else
            CustomerEntity(name = name)

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
