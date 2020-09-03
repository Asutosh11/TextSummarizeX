package com.thoughtleaf.textsummarizex.data

import kotlinx.coroutines.*
import retrofit2.HttpException

class Network {

    companion object {

        fun defaultError(t: Throwable) {
            t.printStackTrace()
        }

        fun <T> request(call: Deferred<T>, callback: NetworkCallback<T>) {
            request(call, callback.success, callback.error)
        }

        fun <T> request(call: Deferred<T>, onSuccess: ((T) -> Unit)?, onError: ((Throwable) -> Unit)?) {

            GlobalScope.async (Dispatchers.Main)  {
                try {
                    onSuccess?.let {
                        onSuccess(call.await())
                    }
                } catch (httpException: HttpException) {
                    // a non-2XX response was received
                    defaultError(httpException)
                } catch (t: Throwable) {
                    // a networking or data conversion error
                    onError?.let {
                        onError(t)
                    }
                }
            }
        }
    }

}