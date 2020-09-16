package com.thoughtleaf.textsummarizex.ui.model

import com.thoughtleaf.textsummarizex.util.AppConstantsUtil

data class SavedSummary(val summary: String? = null, val timeStamp:String? = null, val summaryId:Long = -1, val isSummaryRead : Boolean? = false, val summarizedFrom: String? = AppConstantsUtil.DOCUMENT_TYPE_WEB)