package com.thoughtleaf.textsummarizex.data.db

import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor

@Entity
class MySummariesDAO {

    @Id var id: Long = 0
    val summary:String? = null
    val timeStamp : Long? = 0L

    companion object {

        fun saveSummarizedText(summariedText : String){
            val itemsListBox: Box<MySummariesDAO> = ObjectBoxUtil.boxStore.boxFor()
            val summary : MySummariesDAO = MySummariesDAO(summariedText, System.currentTimeMillis())
            itemsListBox.put(summary)
        }

        fun getAllSumaries() : List<MySummariesDAO> {
            val itemsListBox: Box<MySummariesDAO> = ObjectBoxUtil.boxStore.boxFor()
            val result: List<MySummariesDAO> = itemsListBox.query()
                .build().find()
            return result
        }
    }

    constructor(summariedText: String, currentTimeMillis: Long)

}