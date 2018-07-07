package com.teamclicker.notificationservice.kafka.listeners

import com.teamclicker.notificationservice.kafka.KafkaTopic
import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import com.teamclicker.notificationservice.mail.MailService
import com.teamclicker.notificationservice.testConfig.extensions.waitForCall
import mu.KLogging
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.TimeUnit


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext
internal class MailListenerTest {
    @MockBean
    lateinit var mailService: MailService

    @Autowired
    lateinit var kafkaEmbedded: KafkaEmbedded

    @Autowired
    lateinit var kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry

    @Autowired
    lateinit var template: KafkaTemplate<String, Any>

    @BeforeAll
    fun setUp() {
        // wait until the partitions are assigned
        for (messageListenerContainer in kafkaListenerEndpointRegistry
            .getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(
                messageListenerContainer,
                kafkaEmbedded.getPartitionsPerTopic()
            )
        }
    }

    @Test
    fun `should receive`() {
        val data = PasswordResetEmailKDTO("humberd2@gmail.com", "hello world")
        template.send(KafkaTopic.PASSWORD_RESET_EMAIL.value, data)
        logger.info { "Sending ${data.javaClass.simpleName}" }
        val latch = waitForCall(mailService.send(data))

        assert(latch.await(1, TimeUnit.SECONDS))

    }

    companion object: KLogging()
}