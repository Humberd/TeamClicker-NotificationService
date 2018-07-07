package com.teamclicker.notificationservice.kafka.listeners

import com.teamclicker.notificationservice.kafka.KafkaTopic
import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import com.teamclicker.notificationservice.mail.MailService
import com.teamclicker.notificationservice.testConfig.extensions.waitForCall
import mu.KLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.TimeUnit


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
internal class MailListenerTest {
    @MockBean
    lateinit var mailService: MailService

    @Autowired
    lateinit var template: KafkaTemplate<String, Any>

    @Test
    fun `should send passwordReset email on passwordReset message`() {
        val data = PasswordResetEmailKDTO("admin@admin.com", "hello world")
        template.send(KafkaTopic.PASSWORD_RESET_EMAIL.value, data)
        logger.info { "Sending ${data.javaClass.simpleName}" }
        val latch = waitForCall(mailService.send(data))

        assert(latch.await(1, TimeUnit.SECONDS))

    }

    companion object : KLogging()
}