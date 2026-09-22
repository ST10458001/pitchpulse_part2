package com.pitchpulse.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidationTest {

    private fun isValidPassword(pw: String): Boolean {
        return pw.length >= 8 &&
            pw.any { it.isUpperCase() } &&
            pw.any { it.isLowerCase() } &&
            pw.any { it.isDigit() }
    }

    @Test
    fun `rejects password shorter than 8 characters`() {
        assertFalse(isValidPassword("Ab1"))
    }

    @Test
    fun `rejects password without a digit`() {
        assertFalse(isValidPassword("Password"))
    }

    @Test
    fun `accepts a valid password`() {
        assertTrue(isValidPassword("Password1"))
    }
}
