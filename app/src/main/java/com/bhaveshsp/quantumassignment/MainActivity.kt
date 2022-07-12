package com.bhaveshsp.quantumassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bhaveshsp.quantumassignment.adapters.SignInSignUpTabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val tabArray : Array<String> = arrayOf("Sign In","Sign Up")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabLayout : TabLayout = findViewById(R.id.tabLayout)
        val viewPager2 : ViewPager2 = findViewById(R.id.viewPager2)
        val adapter = SignInSignUpTabLayoutAdapter(supportFragmentManager,lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager2){ tab, position ->
            tab.text = tabArray[position]
        }.attach()
    }
}