package com.thoughtleaf.textsummarizex.util

import android.content.Context
import com.thoughtleaf.textsummarizex.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder

public class ObjectBoxUtil {

    companion object{

        lateinit var boxStore : BoxStore
        lateinit var builder : BoxStoreBuilder

        fun init(context: Context) {

            builder = MyObjectBox.builder()

            boxStore = builder
                .androidContext(context)
                .build()
        }
    }
}