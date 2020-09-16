package com.thoughtleaf.textsummarizex.data.dao

import com.thoughtleaf.textsummarizex.util.AppConstantsUtil
import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.query
import io.objectbox.query.QueryBuilder

@Entity
class MySummariesDAO {

    @Id var id: Long = 0

    var summary:String? = null
    var timeStamp : Long? = 0L
    var isRead : Boolean? = false
    var summarizedFrom : String? = AppConstantsUtil.DOCUMENT_TYPE_WEB

    companion object {

        fun saveSummarizedText(summariedText : String, summarizedFrom: String){
            var itemsListBox: Box<MySummariesDAO> = ObjectBoxUtil.boxStore.boxFor()
            var summariesDAO : MySummariesDAO = MySummariesDAO()
            summariesDAO.summary = summariedText
            summariesDAO.timeStamp = System.currentTimeMillis()
            summariesDAO.isRead = false
            summariesDAO.summarizedFrom = summarizedFrom
            itemsListBox.put(summariesDAO)
        }

        fun markArticleAsRead(id:Long){
            var itemsListBox: Box<MySummariesDAO> = ObjectBoxUtil.boxStore.boxFor()
            var item: MySummariesDAO = itemsListBox.query()
                .equal(MySummariesDAO_.id, id)
                .build().findFirst()!!
            item.isRead = true
            itemsListBox.put(item)
        }

        fun getAllSumaries() : List<MySummariesDAO> {
            val itemsListBox: Box<MySummariesDAO> = ObjectBoxUtil.boxStore.boxFor()
            val result: List<MySummariesDAO> = itemsListBox.query()
                .notNull(MySummariesDAO_.summary)
                .order(MySummariesDAO_.timeStamp, QueryBuilder.DESCENDING)
                .build().find()
            return result
        }

        fun createDummyData(){
            saveSummarizedText("The I’m Feeling Lucky button got its name from the gambling sense of the term—users effectively gambled that the first result would be what they were looking for. The phrase quickly took on a Google-centric life of its own, showing up in employee tell-all book titles as well as in the title of a Google Assistant trivia game show. Since the feature’s release, I’m feeling lucky has in some cases started to refer to the mechanics of the Google button.", AppConstantsUtil.DOCUMENT_TYPE_WEB)
            saveSummarizedText("I’m feeling lucky conveys hope for or optimism about a chance outcome, especially gambling.", AppConstantsUtil.DOCUMENT_TYPE_PDF)
            saveSummarizedText("A form of the phrase is especially associated with Harry Callahan (Clint Eastwood) in the classic 1977 western Dirty Harry. Callahan aims his gun at an enemy on the ground, his gun just out of reach, and has his foe entertain whether or not his cylinder has any remaining bullets: “But being this is a .44 Magnum, the most powerful handgun in the world and would blow your head clean off, you’ve gotta ask yourself one question: ‘Do I feel lucky?’ Well, do ya, punk?” A popular corruption of the quote is “Do you feel lucky, punk?”", AppConstantsUtil.DOCUMENT_TYPE_TXT)
        }
    }

    constructor()

}