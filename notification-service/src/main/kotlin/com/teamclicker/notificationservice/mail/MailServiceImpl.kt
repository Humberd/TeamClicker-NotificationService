package com.teamclicker.notificationservice.mail

import com.teamclicker.notificationservice.Constants.MAIL_TEMPLATES_PATH
import com.teamclicker.notificationservice.kafka.dto.PasswordResetEmailKDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import javax.mail.internet.InternetAddress

@Service
class MailServiceImpl(
    private val mailSender: JavaMailSender,
    private val mailTemplateParser: MailTemplateParser,
    private val resourceLoader: ResourceLoader,
    @Value("\${spring.mail.username}")
    private val senderEmail: String
) : MailService {

    val passwordResetEvent = MailEvent(
        templateFileName = "password_reset",
        mailTitle = "Reset your Password"
    )

    override fun send(data: PasswordResetEmailKDTO) {
        passwordResetEvent.send(
            templateVars = mapOf(
                "password_reset_link" to "https://google.com/"
            ),
            recipent = data.email
        )
    }

    inner class MailEvent(
        templateFileName: String,
        private val mailTitle: String
    ) {
        private val template: String

        init {
            template = readFile(templateFileName)
        }

        fun send(templateVars: Map<String, Any>, recipent: String) {
            val parsedTemplate = mailTemplateParser.parseTemplate(template)
                .replace(templateVars)
                .build()

            val message = MimeMessageHelper(mailSender.createMimeMessage(), false, Charsets.UTF_8.displayName()).let {
                it.setTo(recipent)
                it.setFrom(InternetAddress(senderEmail, "TeamClicker"))
                it.setSubject(mailTitle)
                it.setText(parsedTemplate, true)
                it.mimeMessage
            }

            mailSender.send(message)
        }


        private fun readFile(fileName: String) =
            resourceLoader.getResource("${MAIL_TEMPLATES_PATH}/$fileName.html").file.readText()
    }
}