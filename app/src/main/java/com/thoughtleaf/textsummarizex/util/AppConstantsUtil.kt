package com.thoughtleaf.textsummarizex.util

class AppConstantsUtil {

    companion object{

        sealed class DocumentType {
            class PDF() : DocumentType()
            class DOC() : DocumentType()
            class DOCX() : DocumentType()
            class TXT() : DocumentType()
        }
    }

}