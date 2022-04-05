package com.akashi.roadmap.proxyPattern.bean

data class WeatherBean(
    val result: String,
    val reason: String,
    val error_code: Int,
    val resultCode: String
)