package com.akashi.roadmap

import com.akashi.roadmap.annotation.annotations.Week
import com.akashi.roadmap.annotation.annotations.WeekDay
import org.junit.Assert
import org.junit.Test

class Test {

    @Test
    fun run_grammar_annotation() {

        val d1 = test_weekday(WeekDay.MONDAY)
        val d2 = test_weekday(WeekDay.TUESDAY)
        // Kotlin1.0.3以后已经不支持@IntDef annotation了，编译不报错
        val d3 = test_weekday(11)

        Assert.assertEquals(d1, WeekDay.MONDAY)
        Assert.assertEquals(d2, WeekDay.TUESDAY)
        // Kotlin1.0.3以后已经不支持@IntDef annotation了，编译不报错
        Assert.assertEquals(d3, 11)
    }

    private fun test_weekday(@WeekDay day: Int) = day

    @Test
    fun run_enum_annotation() {
        val week = Week()
        week.day = WeekDay.MONDAY
        Assert.assertEquals(week.day, WeekDay.MONDAY)
    }
}