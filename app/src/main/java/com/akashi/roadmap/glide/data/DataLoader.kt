package com.akashi.roadmap.glide.data

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DataLoader : Runnable {

    companion object {
        private val TAG = DataLoader::class.simpleName
    }

    private lateinit var responseListener: ResponseListener
    private lateinit var context: Context
    private lateinit var path: String

    fun loadResource(path: String, context: Context, responseListener: ResponseListener) {
        this.path = path
        this.context = context
        this.responseListener = responseListener
        val uri = Uri.parse(path)
        val schema = uri.scheme?.uppercase()
        if ("HTTP" == schema || "HTTPS" == schema) {
            ThreadPoolExecutor(
                0,
                Int.MAX_VALUE, 60,
                TimeUnit.SECONDS,
                SynchronousQueue<Runnable>()
            ).execute(this)
        }
    }

    override fun run() {
        var inputStream: InputStream? = null
        var httpURLConnection: HttpURLConnection? = null

        try {
            val url = URL(path);
            val urlConnection = url.openConnection()
            httpURLConnection = urlConnection as HttpURLConnection
            httpURLConnection.connectTimeout = 5000;
            val responseCode = httpURLConnection.responseCode;
            if (HttpURLConnection.HTTP_OK == responseCode) {
                inputStream = httpURLConnection.inputStream;
                val bitmap = BitmapFactory.decodeStream(inputStream);

                // 成功 切换主线程
                Handler(Looper.getMainLooper()).post {
                    val value = EngineResource()
                    value.mBitmap = bitmap

                    // 回调成功
                    responseListener.onSuccess(value);
                }
            } else {
                // 失败 切换主线程
                Handler(Looper.getMainLooper()).post {
                    responseListener.onException(IllegalStateException("请求失败 请求码:$responseCode"))
                }
            }

        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (e: IOException) {
                    e.printStackTrace();
                    Log.d(Companion.TAG, "run: 关闭 inputStream.close(); e:" + e.message);
                }
            }

            httpURLConnection?.disconnect()
        }
    }

}

interface ResponseListener {
    fun onSuccess(engineResource: EngineResource)
    fun onException(e: Throwable)
}