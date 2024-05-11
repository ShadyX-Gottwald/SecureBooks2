package com.example.securebooks2

import com.example.securebooks2.Activities.Utilities.UserProfileUtils
import junit.framework.Assert
import org.junit.Test

class UserProfileTest {


    @Test
    fun `name should be shady`() {
        val name =  UserProfileUtils.getUsername("shady@gmail.com")

        Assert.assertEquals("shady" ,name)
    }
}