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
import androidx.fragment.app.viewModels
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.ui.viewmodel.FirstFragmentVM
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil
import com.thoughtleaf.textsummarizex.util.DocumentReaderUtil
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.File

class FirstFragment : Fragment() {

    private val viewModel: FirstFragmentVM by viewModels()
    val OPEN_REQUEST_CODE = 122
    var docFileExtension : AppConstantsUtil.Companion.DocumentType ?= null
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
            if(!et_enter_url.text?.toString()?.isEmpty()!!){

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
                "text/plain" -> AppConstantsUtil.Companion.DocumentType.TXT()
                "application/pdf" -> AppConstantsUtil.Companion.DocumentType.PDF()
                "application/msword" -> AppConstantsUtil.Companion.DocumentType.DOC()
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> AppConstantsUtil.Companion.DocumentType.DOCX()
                else -> null
            }

            docFileExtension.let {
                viewModel.summarizeFile(File(fileUri?.path), docFileExtension.toString())
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }
}
