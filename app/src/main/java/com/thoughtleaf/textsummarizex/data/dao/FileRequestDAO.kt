package com.thoughtleaf.textsummarizex.model

import com.thoughtleaf.textsummarizex.data.model.FileRequest
import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor
import java.io.File

@Entity
class FileRequestDAO {

    @Id var id: Long = 0
    @Transient var file: File? = null
    var file_extension : String? = null

    companion object {

        fun saveSummarizedText(fileRequest: FileRequest){
            val itemsListBox: Box<FileRequestDAO> = ObjectBoxUtil.boxStore.boxFor()
            val fileRequestDAO:FileRequestDAO = FileRequestDAO()
            fileRequestDAO.file = fileRequest.file
            fileRequestDAO.file_extension = fileRequest.file_extension
            itemsListBox.put(fileRequestDAO)
        }
    }

    constructor()
}