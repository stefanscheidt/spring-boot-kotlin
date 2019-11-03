package com.rewedigital.talks.customerservice

import org.hamcrest.Matchers.emptyOrNullString
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntTest(
    @Autowired val mockMvc: MockMvc
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

        mockMvc
            .get(location) {
                accept = MediaType.APPLICATION_JSON
            }
            .andExpect {
                status { isOk }
                content {
                    json(getCustomerJson(idFrom(location), customerName))
                }
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

    private fun idFrom(location: String) =
        location.split("/").last()

}