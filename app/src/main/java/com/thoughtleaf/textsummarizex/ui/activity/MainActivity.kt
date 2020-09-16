package com.thoughtleaf.textsummarizex.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.data.dao.MySummariesDAO
import com.thoughtleaf.textsummarizex.ui.adapter.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        MySummariesDAO.createDummyData()

        val vpPager = findViewById<View>(R.id.view_pager) as ViewPager
        vpPager.adapter =
            SectionsPagerAdapter(
                applicationContext,
                supportFragmentManager
            )
        vpPager.setCurrentItem(0)

        navView.setOnNavigationItemSelectedListener { item ->

            if(item.getItemId() == R.id.navigation_home){
                vpPager.setCurrentItem(0)
            }
            else if(item.getItemId() == R.id.navigation_dashboard){
                vpPager.setCurrentItem(1)
            }
            item.setChecked(true)

            true

        }

        vpPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                navView.menu.getItem(position).setChecked(true)
            }

            override fun onPageSelected(position: Int) {

            }
        })
    }
}
