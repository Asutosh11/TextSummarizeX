package com.thoughtleaf.textsummarizex.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.data.service.ForegroundService
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil

class ConfirmDialogFragment  : DialogFragment() {

    lateinit var seekbar : SeekBar
    lateinit var strengthTV : TextView
    lateinit var warningTV : TextView
    lateinit var confirmButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.dialog_fragment_confirm, container, false)

        seekbar = root.findViewById(R.id.seek_bar)
        strengthTV = root.findViewById(R.id.strength_text_tv)
        confirmButton = root.findViewById(R.id.confirm_btn)
        warningTV = root.findViewById(R.id.warning_tv)

        if(!AppConstantsUtil.TYPE_OF_API_TO_FIRE.equals(AppConstantsUtil.API_TYPE_SUMMARIZE_URL)){
            warningTV.visibility = View.GONE
        }

        confirmButton.setOnClickListener {
            if(!AppConstantsUtil.IS_FORGROUND_SERVICE_RUNNING){
                ForegroundService.startService(context?.applicationContext!!, "Summarizing Data from URL", AppConstantsUtil.TYPE_OF_API_TO_FIRE!!)
                Toast.makeText(context?.applicationContext!!, "Summarization started. Check the notification area of your device.", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context?.applicationContext!!, "One summarization task is already in progress. Please wait.", Toast.LENGTH_LONG).show()
            }
            dismiss()
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                lateinit var textToShow : String

                when (progress) {
                    0 -> {
                        textToShow = getString(R.string.summary_strength_low)
                        AppConstantsUtil.FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY = 0.6F
                    }
                    1 -> {
                        textToShow = getString(R.string.summary_strength_medium)
                        AppConstantsUtil.FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY = 0.3F
                    }
                    2 -> {
                        textToShow = getString(R.string.summary_strength_high)
                        AppConstantsUtil.FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY = 0.1F
                    }
                    else -> {
                        textToShow = getString(R.string.summary_strength_low)
                        AppConstantsUtil.FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY = 0.3F
                    }
                }
                strengthTV.text = textToShow
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //here we can write some code to do something whenever the user touche the seekbar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)

        dialog.getWindow()?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        return dialog
    }


}