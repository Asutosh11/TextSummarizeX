package com.thoughtleaf.textsummarizex.data.retrofit

import com.thoughtleaf.textsummarizex.model.FileRequestDAO
import com.thoughtleaf.textsummarizex.model.TextRequestDAO
import com.thoughtleaf.textsummarizex.model.UrlRequest
import com.thoughtleaf.textsummarizex.model.UrlRequestDAO
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SummaryService {

    @POST("summary_from_file")
    fun summarizeFile(@Body fileRequest: FileRequestDAO): Call<String>

    @POST("summary_from_text")
    fun summarizeText(@Body fileRequest: TextRequestDAO): Call<String>

    @POST("summary_from_url")
    fun summarizeUrl(@Body fileRequest: UrlRequest): Call<String>
}