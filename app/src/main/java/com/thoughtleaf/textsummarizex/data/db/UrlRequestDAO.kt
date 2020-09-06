package com.thoughtleaf.textsummarizex.model

import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor

@Entity
class UrlRequestDAO() {

    @Id var id: Long = 0
    var url: String? = null

    companion object {

        fun saveUrl(url: String){
            val itemsListBox: Box<UrlRequestDAO> = ObjectBoxUtil.boxStore.boxFor()
            val urlRequestDAO : UrlRequestDAO = UrlRequestDAO()
            urlRequestDAO.url = url
            itemsListBox.put(urlRequestDAO)
        }

        fun getAllSavedURLs() : List<UrlRequestDAO> {
            val itemsListBox: Box<UrlRequestDAO> = ObjectBoxUtil.boxStore.boxFor()
            val result: List<UrlRequestDAO> = itemsListBox.query()
                .build().find()
            return result
        }
    }

}