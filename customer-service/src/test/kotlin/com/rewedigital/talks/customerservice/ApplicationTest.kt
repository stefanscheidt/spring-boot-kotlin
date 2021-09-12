package com.rewedigital.talks.customerservice

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
@AutoConfigureTestDatabase
class ApplicationTest(@Autowired val context: ApplicationContext) {

    @Test
    fun contextLoads() {
        assertThat(context).isNotNull
    }

}
