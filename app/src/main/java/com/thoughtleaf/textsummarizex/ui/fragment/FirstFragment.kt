package com.thoughtleaf.textsummarizex.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.data.service.ForegroundService
import com.thoughtleaf.textsummarizex.model.UrlRequestDAO
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil
import com.thoughtleaf.textsummarizex.util.DocumentReaderUtil
import com.thoughtleaf.textsummarizex.util.ObjectBoxUtil
import io.objectbox.TxCallback
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    val OPEN_REQUEST_CODE = 122
    var docFileExtension : String ?= null
    lateinit var activityContext: Context

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_choose_doc.setOnClickListener { view ->
            chooseFile(view)
        }

        btn_submit_url.setOnClickListener { view ->
            if(!et_enter_url.text?.toString()?.isEmpty()!! && ObjectBoxUtil.boxStore != null){
                ObjectBoxUtil.boxStore.runInTxAsync(object:Runnable {
                    public override fun run() {
                        UrlRequestDAO.saveUrl(et_enter_url.text.toString())
                    }
                }, object: TxCallback<Void> {
                    override fun txFinished(result: Void?, error: Throwable?) {
                        // start the foreground service
                        ForegroundService.startService(activityContext.applicationContext, "Summarizing Data from URL", AppConstantsUtil.API_TYPE_SUMMARIZE_URL)
                    }
                })
            }
        }

        btn_submit_text.setOnClickListener { view ->
            if(!et_enter_text.text?.toString()?.isEmpty()!!){

            }
        }
    }

    fun chooseFile(view: View) {

        val mimeTypes = arrayOf(
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
            "text/plain",
            "application/pdf"
        )

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""
            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }
            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), OPEN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (resultCode == Activity.RESULT_OK && requestCode == OPEN_REQUEST_CODE) {

            val fileUri = resultData?.data!!

            docFileExtension= when (DocumentReaderUtil.getMimeType(fileUri, activityContext)) {
                "text/plain" -> AppConstantsUtil.Companion.DOCUMENT_TYPE_TXT
                "application/pdf" -> AppConstantsUtil.Companion.DOCUMENT_TYPE_PDF
                "application/msword" -> AppConstantsUtil.Companion.DOCUMENT_TYPE_DOC
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> AppConstantsUtil.Companion.DOCUMENT_TYPE_DOCX
                else -> null
            }

            docFileExtension.let {

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }
}
