package com.thoughtleaf.textsummarizex.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.thoughtleaf.textsummarizex.ui.fragment.FirstFragment
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.ui.fragment.SecondFragment

private val TAB_TITLES = arrayOf(R.string.tab_text_1, R.string.tab_text_2)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0){
            return FirstFragment()
        }
        else{
            return SecondFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}