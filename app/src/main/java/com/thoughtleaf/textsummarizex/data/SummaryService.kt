package com.thoughtleaf.textsummarizex.data

import com.thoughtleaf.textsummarizex.model.FileRequest
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SummaryService {

    @POST("summary_from_file")
    fun summarizeFile(@Body fileRequest: FileRequest): Deferred<String>

    @POST("summary_from_text")
    fun summarizeText(@Body fileRequest: FileRequest): Deferred<String>

    @POST("summary_from_url")
    fun summarizeUrl(@Body fileRequest: FileRequest): Deferred<String>
}