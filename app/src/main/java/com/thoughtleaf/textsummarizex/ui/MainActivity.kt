package com.thoughtleaf.textsummarizex.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thoughtleaf.textsummarizex.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val vpPager = findViewById<View>(R.id.view_pager) as ViewPager
        vpPager.adapter = SectionsPagerAdapter(
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

            true

        }
    }
}
