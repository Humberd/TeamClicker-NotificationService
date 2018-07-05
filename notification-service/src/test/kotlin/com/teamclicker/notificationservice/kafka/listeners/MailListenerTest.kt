package com.teamclicker.notificationservice.kafka.listeners

import com.teamclicker.notificationservice.kafka.KafkaTopic
import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import mu.KLogging
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.test.utils.ContainerTestUtils
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
internal class MailListenerTest {

    @Autowired
    lateinit var kafkaEmbedded: KafkaEmbedded

    @Autowired
    lateinit var kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry

    lateinit var template: KafkaTemplate<String, Any>

//    https://stackoverflow.com/questions/48894185/why-is-my-kafka-listener-not-working-in-my-unit-test
    @BeforeAll
    fun setUp() {
        val senderProperties = KafkaTestUtils.senderProps(kafkaEmbedded.getBrokersAsString()).also {
            it.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            it.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
            it.put("auto.offset.reset", "earliest");
        }

        // create a Kafka producer factory
        val producerFactory = DefaultKafkaProducerFactory<String, Any>(senderProperties)

        // create a Kafka template
        template = KafkaTemplate(producerFactory)

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
        logger.info { "Send $data" }
    }

    companion object: KLogging()
}