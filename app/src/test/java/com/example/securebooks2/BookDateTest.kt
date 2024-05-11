package com.example.securebooks2

import com.example.securebooks2.Activities.Utilities.DateUtils
import org.junit.Test
import java.util.Date
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class BookDateTest {

    private val data: DateUtils
        get() {
          return DateUtils()
        }

    @Test
    fun `should match date format`() {

        val date = "12/3/2000"

        val split = date.split("/")

//        val day = split[0]
//        val month = split[1]
//        val year = split[2]
        val res =  DateUtils.validDateMatch(date)

        assertTrue(res)

    }



}