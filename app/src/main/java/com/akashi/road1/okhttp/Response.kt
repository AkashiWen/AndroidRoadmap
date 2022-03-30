package com.akashi.road1.okhttp

class Response {

    var body: Any? = null

    class Builder {

        fun buildEmptyBodyResponse(): Response {
            return Response()
        }

        fun buildFakeDataResponse(): Response {
            return Response().apply {
                body = "json response received"
            }
        }
    }
}