package com.thoughtleaf.textsummarizex.data

class NetworkCallback<T> {
    var success: ((T) -> Unit) ?= null
    var error: ((Throwable)-> Unit) ?= null
}