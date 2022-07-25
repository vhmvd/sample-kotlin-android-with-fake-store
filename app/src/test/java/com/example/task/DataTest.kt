package com.example.task

import com.example.task.data.models.Cart
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataTest  {

    private val data = Cart(1,4.0F,"asd", "dsa", 1.0)

    @Test
    fun idTest(){
        assertEquals(1, data.id)
    }

    @Test
    fun urlTest(){
        assertEquals("asd", data.image)
    }

    @Test
    fun titleTest(){
        assertEquals("dsa", data.title)
    }

    @Test
    fun priceTest(){
        assertEquals(1.0, data.price)
    }

}