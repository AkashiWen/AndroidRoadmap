package com.akashi.annotationcompiler

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * 注解处理程序
 * 1.打注解@AutoService, 注册Process.class
 * 2.继承AbstractProcessor
 */
@AutoService(Process::class)
class AnnotationCompiler : AbstractProcessor() {
    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        TODO("Not yet implemented")
    }
}