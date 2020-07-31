package com.thoughtleaf.textsummarizex

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.thoughtleaf.textsumarizex.DocumentReaderUtil

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val OPEN_REQUEST_CODE = 122

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        button.setOnClickListener { view ->
            openFile(view)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openFile(view: View) {

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

            var docString : String = when (DocumentReaderUtil.getMimeType(fileUri, applicationContext)) {
                "text/plain" -> DocumentReaderUtil.readTxtFromUri(fileUri, applicationContext)
                "application/pdf" -> DocumentReaderUtil.readPdfFromUri(fileUri, applicationContext)
                "application/msword" -> DocumentReaderUtil.readWordDocFromUri(fileUri, applicationContext)
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ->
                    DocumentReaderUtil.readWordDocFromUri(fileUri, applicationContext)
                else -> ""
            }

            Log.i("docString", docString)
        }
    }

}
