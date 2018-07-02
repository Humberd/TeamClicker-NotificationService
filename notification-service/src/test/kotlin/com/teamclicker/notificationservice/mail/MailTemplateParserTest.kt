package com.teamclicker.notificationservice.mail

import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MailTemplateParserTest {
    lateinit var mailTemplateParser: MailTemplateParser

    @BeforeAll
    fun setupAll() {
        mailTemplateParser = MailTemplateParser()
    }

    @Test
    fun `should return the same template when no variables were added`() {
        @Language("XML")
        val template = """
            <mj-text>
                Hello Adventurer
            </mj-text>
        """.trimIndent()

        val newTemplate = mailTemplateParser.parseTemplate(template)
            .build()

        assertEquals(template, newTemplate)
    }

    @Test
    fun `should replace a variable with a value when it is available in a template`() {
        @Language("XML")
        val template = """
            <mj-text>
                Hello [[username]]
            </mj-text>
        """.trimIndent()

        val newTemplate = mailTemplateParser.parseTemplate(template)
            .replace("username", "Alice")
            .build()

        @Language("XML")
        val expectedTemplate = """
            <mj-text>
                Hello Alice
            </mj-text>
        """.trimIndent()
        assertEquals(expectedTemplate, newTemplate)
    }

    @Test
    fun `should replace multiple variables with values`() {
        @Language("XML")
        val template = """
            <mj-text>
                Hello [[username]]([[age]]) from [[country_name]]
            </mj-text>
        """.trimIndent()

        val newTemplate = mailTemplateParser.parseTemplate(template)
            .replace("username", "Alice")
            .replace("age", 8)
            .replace("country_name", "England")
            .build()

        @Language("XML")
        val expectedTemplate = """
            <mj-text>
                Hello Alice(8) from England
            </mj-text>
        """.trimIndent()
        assertEquals(expectedTemplate, newTemplate)
    }

    @Test
    fun `should not replace a variable with a value when the tags are wrong`() {
        @Language("XML")
        val template = """
            <mj-text>
                Hello {{username}}
            </mj-text>
        """.trimIndent()

        val newTemplate = mailTemplateParser.parseTemplate(template)
            .replace("username", "Alice")
            .build()

        assertEquals(template, newTemplate)
    }
}