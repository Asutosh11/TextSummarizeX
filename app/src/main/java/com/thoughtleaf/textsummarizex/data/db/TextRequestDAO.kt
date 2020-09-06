package com.thoughtleaf.textsummarizex.model

import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor

@Entity
class TextRequestDAO {

    @Id var id: Long = 0
    var text: String? = null

    companion object {

        fun saveSummarizedText(url: TextRequest){
            val itemsListBox: Box<TextRequest> = ObjectBoxUtil.boxStore.boxFor()
            itemsListBox.put(url)
        }
    }
}