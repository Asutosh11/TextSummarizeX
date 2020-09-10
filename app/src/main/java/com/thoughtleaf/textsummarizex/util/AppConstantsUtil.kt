package com.thoughtleaf.textsummarizex.util

class AppConstantsUtil {

    companion object{

        val DOCUMENT_TYPE_TXT = "txt"
        val DOCUMENT_TYPE_PDF = "pdf"
        val DOCUMENT_TYPE_DOC = "doc"
        val DOCUMENT_TYPE_DOCX = "docx"

        var TYPE_OF_API_TO_FIRE: String? = null
        val API_TYPE_SUMMARIZE_FILE: String = "summarizeFile"
        val API_TYPE_SUMMARIZE_TEXT: String = "summarizeText"
        val API_TYPE_SUMMARIZE_URL: String = "summarizeUrl"

        var FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY: Float = 0.3F
        var IS_FORGROUND_SERVICE_RUNNING: Boolean = false
    }


}