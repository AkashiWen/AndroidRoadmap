package com.akashi.annotations

/**
 * 普通注解
 * 接收Int类型value
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)// 写到哪种东西上（字段、类、函数等等）
@Retention(AnnotationRetention.SOURCE) // 注解生命周期
annotation class BindView(
    val value: Int
)