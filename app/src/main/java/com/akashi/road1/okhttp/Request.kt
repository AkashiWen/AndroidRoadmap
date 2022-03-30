package com.akashi.road1.okhttp

class Request {
    private var call: Call? = null

    private var method: String = "GET"
    private var headers: Map<String, String>? = null
    private var requestBody: RequestBody? = null
    private var httpUrl: HttpUrl? = null

    fun setHttpUrl(url: String): Request {
        this.httpUrl = HttpUrl(url)
        return this
    }

    fun doGet(): Request {
        this.method = "GET"
        return this

    }

    fun doPost(requestBody: RequestBody): Request {
        this.requestBody = requestBody
        this.method = "POST"
        return this
    }

    class Builder {
        private lateinit var url: String

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun build(): Request {
            val request = Request().also {
                it.setHttpUrl(url)
            }
            return request
        }
    }
}