package com.thoughtleaf.textsummarizex.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

class DocumentReaderUtil {

    companion object {

        /**
         * @param uri - uri of the file on device
         * @param context - context object
         */
        fun getMimeType(uri: Uri, context: Context?): String? {
            val contentResolver: ContentResolver = context?.contentResolver!!
            return contentResolver.getType(uri)
        }

    }

}