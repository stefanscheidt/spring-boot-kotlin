package com.rewedigital.talks.customerservice

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.emptyOrNullString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Sql(statements = ["truncate table customers"])
class CustomerControllerIntTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val jdbcTemplate: JdbcTemplate
) {

    @Test
    fun `post and get customer`() {
        val customerName = "John Doe"
        val location = mockMvc
            .post(CUSTOMERS_ENDPOINT) {
                contentType = MediaType.APPLICATION_JSON
                content = createCustomerJson(name = customerName)
            }
            .andExpect {
                status { isCreated }
                header { string("location", not(emptyOrNullString())) }
            }
            .andReturn().response.getHeaderValue("location") as String
        val id = idFromLocation(location)

        assertThat(nameFromDB(id)).isEqualTo(customerName)

        mockMvc
            .get(location) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isOk }
                content {
                    json(getCustomerJson(id, customerName))
                }
            }
    }

    @Test
    fun `get with unknown customer id`() {
        val location = "$CUSTOMERS_ENDPOINT/${UUID.randomUUID()}"
        mockMvc
            .get(location) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isNotFound }
                content { string(emptyOrNullString()) }
            }
    }

    @Test
    fun `get with bad customer id`() {
        val location = "$CUSTOMERS_ENDPOINT/__this_is_not_a_uuid__"
        mockMvc
            .get(location) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isBadRequest }
                content { string(emptyOrNullString()) }
            }
    }

    private fun getCustomerJson(id: String, name: String) =
        """
            {
                "id": "$id",
                "name": "$name"
            }
            
        """.trimIndent()

    private fun createCustomerJson(name: String) =
        """
            {
                "name": "$name"
            }
        """.trimIndent()

    private fun idFromLocation(location: String) =
        location.split("/").last()

    private fun nameFromDB(id: String) =
        jdbcTemplate.queryForObject<String>("select name from customers where id = ?", arrayOf(id))

}