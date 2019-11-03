package com.rewedigital.talks.customerservice

import org.springframework.data.repository.CrudRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


interface CustomerRepository : CrudRepository<CustomerEntity, String>

@Entity
data class CustomerEntity(
    @Id
    @Column(columnDefinition = "CHAR", length = 36, nullable = false, updatable = false)
    val id: String,
    val name: String
)