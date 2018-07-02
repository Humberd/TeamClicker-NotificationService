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

    fun readFile(fileName: String) = resourceLoader.getResource("${MAIL_TEMPLATES_PATH}/$fileName.html").file.readText()

    fun send(data: PasswordResetEmailKDTO) {
        val template = readFile("password_reset")
        val parsedTemplate = mailTemplateParser.parseTemplate(template)
            .replace("password_reset_link", "https://google.com/")
            .build()

        val message = MimeMessageHelper(mailSender.createMimeMessage(), false, Charsets.UTF_8.displayName()).let {
            it.setTo(data.email)
            it.setFrom(InternetAddress(senderEmail, "TeamClicker"))
            it.setSubject("Reset your Password")
            it.setText(parsedTemplate, true)
            it.mimeMessage
        }

        mailSender.send(message)
    }
}