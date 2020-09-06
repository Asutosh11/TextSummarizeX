package com.thoughtleaf.textsummarizex.util

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketException
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLException


class NetworkUtil {

    companion object {

        fun getErrorMessage(error: Throwable) : String{
            if (error is SocketException) {
                return ("Timed Out")
            }
            else if (error is HttpException) {
                when (error.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> return ("Unauthorised User ")
                    HttpsURLConnection.HTTP_FORBIDDEN -> return ("Forbidden")
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> return ("Internal Server Error")
                    HttpsURLConnection.HTTP_BAD_REQUEST -> return ("Bad Request")
                    else -> return (error.getLocalizedMessage())
                }
            }
            else {
                return ("The Service isnt up. Try after sometime.")
            }
        }
    }
}