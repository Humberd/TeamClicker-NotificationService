package com.teamclicker.notificationservice.mail

import com.teamclicker.notificationservice.Constants.MAIL_TEMPLATE_VARIABLE_END
import com.teamclicker.notificationservice.Constants.MAIL_TEMPLATE_VARIABLE_START
import org.springframework.stereotype.Service

@Service
class MailTemplateParser {
    fun parseTemplate(template: String): MailTemplateBuilder {
        return MailTemplateBuilder(template)
    }

    class MailTemplateBuilder(private val template: String) {
        private val variables = hashMapOf<String, String>()

        fun replace(variableName: String, value: String): MailTemplateBuilder {
            variables.set(variableName, value)
            return this
        }

        fun replace(variableName: String, value: Number): MailTemplateBuilder {
            return replace(variableName, value.toString())
        }

        fun build(): String {
            var currentTemplate = template
            for ((varName, value) in variables) {
                currentTemplate =
                        currentTemplate.replace(
                            "${MAIL_TEMPLATE_VARIABLE_START}$varName${MAIL_TEMPLATE_VARIABLE_END}",
                            value
                        )
            }

            return currentTemplate
        }
    }
}