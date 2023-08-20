package com.example.punnetverse

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.punnetverse.adapters.TabsPagerAdapter
import com.example.punnetverse.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.material.tabs.TabLayoutMediator

// Goals
//1. Invoke addViews with video id endpoint after playing a video -> added
//2. After search if user try to back it should navigate to home page rather than closing applications-> not required right now
//3. load page size to 5 elements rather than 10 -> done
//4. optimise video player -> in future
//5. Memes tab should be renamed to Trending and load trendings endpoints Data - done

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabNames = listOf("Templates","Trending")
    lateinit var mAdView : AdView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Hide the status bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark_color)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpViewPager()
        setContentView(binding.root)
        loadBannerAd()
    }

    private fun loadBannerAd() {
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                //to count ads click
                Toast.makeText(this@MainActivity,"Opening web..",Toast.LENGTH_SHORT).show()
            }

            override fun onAdImpression() {
                //needed to save record of impression
            }
        }
    }

    private fun setUpViewPager() {
        binding.pager.adapter = TabsPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}