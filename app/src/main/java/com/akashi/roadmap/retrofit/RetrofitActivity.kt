package com.akashi.roadmap.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.akashi.roadmap.R
import com.akashi.roadmap.common.clickJitter
import com.akashi.roadmap.retrofit.subject.ISubject
import com.akashi.roadmap.retrofit.subject.RealSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        findViewById<AppCompatButton>(R.id.btn).clickJitter {
            proxy()
        }

    }

    /**
     * 示例Retrofit使用
     */
    private fun callRequest() {
        // create动态代理
        val accountApi = retrofit.create(AccountApi::class.java)
        // 获得Call对象
        val call = accountApi.getProfile()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    /**
     * 进行动态代理，并执行
     */
    private fun proxy() {
        val realSubject = RealSubject()
        val cls = realSubject.javaClass
        val proxy = Proxy.newProxyInstance(
            cls.classLoader,
            cls.interfaces,
            DynamicProxy(realSubject)
        ) as ISubject

        proxy.buySth()
    }

}