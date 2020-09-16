package com.thoughtleaf.textsummarizex.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.data.dao.MySummariesDAO
import com.thoughtleaf.textsummarizex.ui.activity.FullSummaryActivity
import com.thoughtleaf.textsummarizex.ui.adapter.SavedSummariesAdapter
import com.thoughtleaf.textsummarizex.ui.model.SavedSummary


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), SavedSummariesAdapter.CardClickListener {

    lateinit var summariesRV: RecyclerView
    var activityContext: Context? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        summariesRV = view.findViewById(R.id.summaries_rv) as RecyclerView
        val savedSummaries = MySummariesDAO.getAllSumaries()
        val summaryListForRV: ArrayList<SavedSummary> = ArrayList()
        for(summary in savedSummaries){
            summaryListForRV.add(SavedSummary(summary.summary, summary.timeStamp.toString(), summary.id, summary.isRead, summary.summarizedFrom))
        }

        val adapter = SavedSummariesAdapter(summaryListForRV, context?.applicationContext, this)
        summariesRV.setAdapter(adapter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
    }

    override fun onCardCLicked(summary: String?) {
        val intent = Intent(activityContext, FullSummaryActivity::class.java)
        intent.putExtra("fullSummary", summary)
        startActivity(intent)
    }


}
