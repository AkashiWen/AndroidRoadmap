package com.akashi.roadmap.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit

// 自定义OkhttpClient
val okhttpClient = OkHttpClient.Builder()
//    .addInterceptor()
    .build()

// 建造者builder创建Okhttp和callback线程池（主线程回调）
val retrofit = Retrofit.Builder()
    .client(okhttpClient)
    .baseUrl("").build()
