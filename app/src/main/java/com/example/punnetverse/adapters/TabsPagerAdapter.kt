package com.example.punnetverse.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.punnetverse.fragments.MemeFragment
import com.example.punnetverse.fragments.TemplatesFragment

class TabsPagerAdapter (fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TemplatesFragment.newInstance("", "")
            else -> MemeFragment.newInstance("","")
        }
    }
}