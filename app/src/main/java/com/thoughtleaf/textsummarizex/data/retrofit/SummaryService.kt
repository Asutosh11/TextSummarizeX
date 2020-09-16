package com.thoughtleaf.textsummarizex.data.retrofit

import com.thoughtleaf.textsummarizex.data.model.FileRequest
import com.thoughtleaf.textsummarizex.data.model.TextRequest
import com.thoughtleaf.textsummarizex.data.model.UrlRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SummaryService {

    @POST("summary_from_file")
    fun summarizeFile(@Body request: FileRequest): Call<String>

    @POST("summary_from_text")
    fun summarizeText(@Body request: TextRequest): Call<String>

    @POST("summary_from_url")
    fun summarizeUrl(@Body request: UrlRequest): Call<String>
}