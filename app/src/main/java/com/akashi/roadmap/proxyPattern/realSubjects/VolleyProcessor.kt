package com.akashi.roadmap.proxyPattern.realSubjects

import android.content.Context
import com.akashi.roadmap.proxyPattern.ICallback
import com.akashi.roadmap.proxyPattern.IHttpProcessor
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class VolleyProcessor(context: Context) : IHttpProcessor {

    private val mQueue = Volley.newRequestQueue(context)

    override fun post(url: String, params: Map<String, Any>, callback: ICallback) {
        val request = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                callback.onSuccess(it)
            },
            {
                callback.onFailure()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return mutableMapOf<String, String>().apply {
                    for (entry in params.entries) {
                        this[entry.key] = entry.value.toString()
                    }
                }
            }
        }

        mQueue.add(request)
    }
}