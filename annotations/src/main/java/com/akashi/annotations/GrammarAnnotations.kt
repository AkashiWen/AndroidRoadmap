package com.akashi.annotations

import androidx.annotation.IntDef
import com.akashi.annotations.WeekDay.Companion.MONDAY
import com.akashi.annotations.WeekDay.Companion.TUESDAY


/**
 * 语法注解，同时可做枚举
 * 定义只能使用MONDAY, TUESDAY
 * But Kotlin1.0.3以后已经不支持@IntDef annotation了
 */
@MustBeDocumented
@IntDef(MONDAY, TUESDAY)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class WeekDay {
    companion object {
        const val MONDAY = 1
        const val TUESDAY = 2
    }
}
