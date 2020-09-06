package com.thoughtleaf

import android.app.Application
import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBoxUtil.init(this)
    }
}