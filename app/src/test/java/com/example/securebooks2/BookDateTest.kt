package com.example.securebooks2

import org.junit.Test
import java.util.Date
import org.junit.Assert
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class BookDateTest {


    @Test
    fun `invalid date format`() {

        val date = "12/3/2000"

        date.split("/")

        val dateparse = Date()


        val result = isDateValid(date)
        Assert.assertTrue(result)

    }

    fun isDateValid(dateString: String): Boolean {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            return true
        } catch (e: DateTimeParseException) {
            return false
        }
    }


}