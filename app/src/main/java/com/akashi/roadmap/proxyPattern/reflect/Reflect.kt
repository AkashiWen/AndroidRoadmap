package com.akashi.roadmap.proxyPattern.reflect

import java.lang.reflect.ParameterizedType

/**
 * 利用反射分析类信息
 * @return 泛型类型的字节码对象
 */
fun analysisClassInfo(obj: Any): Class<*> {
    // 获取实际类class，as泛型尖括号中的类型
    val type = obj.javaClass.genericSuperclass as ParameterizedType
    val params = type.actualTypeArguments
    return params[0] as Class<*>
}