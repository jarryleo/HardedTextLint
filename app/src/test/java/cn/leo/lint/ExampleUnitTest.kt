package cn.leo.lint

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)

        //val reg = "([\\u4e00-\\u9fa5]+[^\"]*)".toRegex()
        val reg = "(.*[\\u4e00-\\u9fa5]+.*)".toRegex()
        val value = "中文硬编码"
        val value1 = "硬编码1 "
        val value2 = "1111"
        val value3 = "1硬编码1\" "
        val result = value.matches(reg)
        val result1 = value1.matches(reg)
        val result2 = value2.matches(reg)
        val result3 = value3.matches(reg)
        assertEquals(true, result)
        assertEquals(true, result1)
        assertEquals(false, result2)
        assertEquals(true, result3)
    }
}