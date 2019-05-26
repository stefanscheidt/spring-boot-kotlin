package com.rewedigital.talks.customerservice

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.repository.CrudRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


interface CustomerRepository : CrudRepository<CustomerEntity, String>

@Entity
data class CustomerEntity(
    @Id
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "CHAR", length = 36, nullable = false, updatable = false)
    val id: String? = null,
    val name: String
)