package com.akashi.roadmap.proxyPattern.module

import com.akashi.roadmap.MainApplication
import com.akashi.roadmap.proxyPattern.IHttpProcessor
import com.akashi.roadmap.proxyPattern.realSubjects.OkhttpProcessor
import com.akashi.roadmap.proxyPattern.realSubjects.VolleyProcessor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val proxyPatternModule = module {
    single<IHttpProcessor> { OkhttpProcessor() }
    single<IHttpProcessor>(named("volley")) { VolleyProcessor(MainApplication.instance) }
}