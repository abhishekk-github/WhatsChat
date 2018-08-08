package com.genius.amateur.whatschat.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.genius.amateur.whatschat.fragments.Chatsfragment
import com.genius.amateur.whatschat.fragments.UsersFragment

class SectionPagerAdapter(framgmentManager : FragmentManager) : FragmentPagerAdapter(framgmentManager){
    override fun getItem(position: Int): Fragment {

        when(position){
            0->{
                return UsersFragment()
            }
            1->{
                return Chatsfragment()
            }
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Friends"
            }
            1 -> {
                return "Chats"
            }
        }
        return null!!
    }
}