package com.rewedigital.talks.customerservice

import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Types
import java.util.*


data class Customer(
    val id: UUID,
    val name: String
)

@Repository
class CustomerRepository(private val jdbcTemplate: JdbcTemplate) {

    fun findById(id: UUID): Customer? {
        val found = jdbcTemplate.query(
            "select * from customers where id = ?",
            arrayOf(id.toString()),
            intArrayOf(Types.VARCHAR)
        ) { rs, _ ->
            Customer(
                id = UUID.fromString(rs.getString("id")),
                name = rs.getString("name")
            )
        }
        return DataAccessUtils.singleResult(found)
    }

    fun save(customer: Customer) {
        jdbcTemplate.update(
            "insert into customers(id, name) values (?, ?)",
            customer.id.toString(), customer.name
        )
    }

}