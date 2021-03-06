package com.teamclicker.notificationservice.mail

import com.teamclicker.notificationservice.Constants.MAIL_TEMPLATE_VARIABLE_END
import com.teamclicker.notificationservice.Constants.MAIL_TEMPLATE_VARIABLE_START
import mu.KLogging
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

        fun replace(map: Map<String, Any>): MailTemplateBuilder {
            for ((key, value) in map) {
                replace(key, value.toString())
            }

            return this
        }

        fun build(): String {
            var currentTemplate = template
            for ((varName, value) in variables) {
                val parsedVar = "$MAIL_TEMPLATE_VARIABLE_START$varName$MAIL_TEMPLATE_VARIABLE_END"
                if (currentTemplate.indexOf(parsedVar) == -1) {
                    logger.warn { "Variable $parsedVar does not exist in a template" }
                }
                currentTemplate = currentTemplate.replace(parsedVar, value)
            }

            return currentTemplate
        }
    }

    companion object : KLogging()
}