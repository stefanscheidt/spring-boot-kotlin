package com.rewedigital.talks.customerservice

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.jdbc.Sql
import java.util.*


@DataJdbcTest
@Sql(statements = ["truncate table customers"])
class CustomerRepositoryTest(@Autowired jdbcTemplate: JdbcTemplate) {

    private val customerRepository = CustomerRepository(jdbcTemplate)

    @Test
    fun `save and find`() {
        val customer = Customer(id = someUUID(), name = "John Doe")

        customerRepository.save(customer)

        assertThat(customerRepository.findById(customer.id)).isEqualTo(customer)
        assertThat(customerRepository.findById(someUUID())).isNull()
    }

    @Test
    fun `save twice`() {
        val sameId = someUUID()
        customerRepository.save(Customer(id = sameId, name = "John Doe"))

        assertThrows<DuplicateKeyException> {
            customerRepository.save(Customer(id = sameId, name = "John Doe"))

        }
    }

    private fun someUUID() = UUID.randomUUID()

}