package com.teamclicker.notificationservice.mail

import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MailServiceImplTest {
    @Autowired
    lateinit var mailServiceImpl: MailServiceImpl

    @Test
    fun `should foo`() {
//        mailServiceImpl.send(
//            PasswordResetEmailKDTO(
//                "humberd2@gmail.com", "abcd1234"
//            )
//        )
    }
}