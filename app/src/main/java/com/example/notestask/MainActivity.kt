package com.example.notestask

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalArgumentException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout=findViewById<TabLayout>(R.id.navigationView)
        viewPager=findViewById<ViewPager2>(R.id.flFragment)
        val viewPageAdapter=ViewPageAdapter(this)
        viewPager.adapter=viewPageAdapter
        TabLayoutMediator(tabLayout,viewPager){tab, position ->
            tab.text=when(position){
                0 -> "Notes"
                1 -> "Tasks"
                else -> "Unknown"
            }
        }.attach()
    }
}
class ViewPageAdapter(activity:AppCompatActivity):FragmentStateAdapter(activity){
    override fun getItemCount(): Int=2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> NotesFragment()
            1 -> TasksFragment()
            else -> throw IllegalArgumentException("Invalid Position $position")
        }
    }
}