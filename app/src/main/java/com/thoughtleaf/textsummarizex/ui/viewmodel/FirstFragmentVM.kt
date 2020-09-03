package com.thoughtleaf.textsummarizex.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.thoughtleaf.textsummarizex.data.ApiProvider
import com.thoughtleaf.textsummarizex.data.Network
import com.thoughtleaf.textsummarizex.data.NetworkCallback
import com.thoughtleaf.textsummarizex.model.FileRequest
import java.io.File

class FirstFragmentVM : ViewModel() {

    fun summarizeFile(file: File, file_extension: String){

        Network.request(
            ApiProvider.provideApi().summarizeFile(FileRequest(file, file_extension)),
            NetworkCallback<String>().apply {
                success = {
                    // it.  (User)
                }

                error = {
                    // it.  (Throwable)
                }
            })
    }
}