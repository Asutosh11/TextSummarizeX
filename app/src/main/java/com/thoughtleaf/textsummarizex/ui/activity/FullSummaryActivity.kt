package com.thoughtleaf.textsummarizex.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.thoughtleaf.textsummarizex.R
import kotlinx.android.synthetic.main.activity_full_summary.*


class FullSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setContentView(R.layout.activity_full_summary)

        intent?.let {
            intent.extras?.let {
                intent.extras!!.getString("fullSummary").let {
                    var fullSummary = intent.extras!!.getString("fullSummary")
                    full_summary_tv.text = fullSummary
                }

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
