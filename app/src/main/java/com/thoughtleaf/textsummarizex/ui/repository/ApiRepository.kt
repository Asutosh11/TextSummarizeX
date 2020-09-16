package com.thoughtleaf.textsummarizex.ui.repository

import com.thoughtleaf.textsummarizex.data.dao.MySummariesDAO
import com.thoughtleaf.textsummarizex.data.retrofit.ApiClient
import com.thoughtleaf.textsummarizex.data.service.ForegroundService
import com.thoughtleaf.textsummarizex.data.model.FileRequest
import com.thoughtleaf.textsummarizex.data.model.TextRequest
import com.thoughtleaf.textsummarizex.data.model.UrlRequest
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil
import com.thoughtleaf.textsummarizex.util.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiRepository {

    constructor(foregroundService: ForegroundService){
        this.foregroundService = foregroundService
    }

    val apiClient: ApiClient = ApiClient()

    var foregroundService: ForegroundService? = null

    fun summarizeFile(fileRequest: FileRequest, documentType: String){

        apiClient.getClient.summarizeFile(fileRequest)
            .enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                MySummariesDAO.saveSummarizedText(response.toString(), AppConstantsUtil.DOCUMENT_TYPE_PDF)
                foregroundService?.closeForgroundService("File summarized. Go to 'Your Summaries'")
            }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    val error = NetworkUtil.getErrorMessage(t)
                    foregroundService?.closeForgroundService("File summarization failed")
                }

            })
    }

    fun summarizeUrl(urlRequest: UrlRequest){

        apiClient.getClient.summarizeUrl(urlRequest)
            .enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    MySummariesDAO.saveSummarizedText(response.toString(), AppConstantsUtil.DOCUMENT_TYPE_WEB)
                    foregroundService?.closeForgroundService("Web Page summarized. Go to 'Your Summaries'")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    val error = NetworkUtil.getErrorMessage(t)
                    foregroundService?.closeForgroundService("Web Page summarization failed")
                }

            })
    }

    fun summarizeText(textRequest: TextRequest){

        apiClient.getClient.summarizeText(textRequest)
            .enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    MySummariesDAO.saveSummarizedText(response.toString(), AppConstantsUtil.DOCUMENT_TYPE_TXT)
                    foregroundService?.closeForgroundService("Text summarized. Go to 'Your Summaries'")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    val error = NetworkUtil.getErrorMessage(t)
                    foregroundService?.closeForgroundService("Text summarization failed")
                }

            })
    }

    interface callback{
        fun closeForgroundService(message: String)
    }

}