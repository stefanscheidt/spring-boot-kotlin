package com.rewedigital.talks.customerservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<CustomerDto> = customerRepository
        .findById(id)
        .map { ResponseEntity.ok(it.toDto()) }
        .orElse(ResponseEntity.notFound().build())

    @PostMapping("/customers")
    fun createCustomer(@RequestBody customer: CustomerDto) = customerRepository
        .save(customer.toEntity())
        .createdResponse()

    private fun CustomerEntity.createdResponse() = ResponseEntity
        .created(URI.create("/customers/${id!!}"))
        .build<CustomerDto>()

    private fun CustomerEntity.toDto() = CustomerDto(id = id, name = name)

}

data class CustomerDto(val id: String?, val name: String) {
    fun toEntity() = CustomerEntity(name = name)
}


