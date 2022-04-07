package com.akashi.roadmap.annotation

import com.akashi.annotations.WeekDay

/**
 * day视为枚举类型
 */
class Week {
    @WeekDay
    var day: Int = WeekDay.MONDAY
}
