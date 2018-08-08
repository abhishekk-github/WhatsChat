package com.genius.amateur.whatschat.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.genius.amateur.whatschat.R
import com.genius.amateur.whatschat.adapters.UsersAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_users.*


class UsersFragment : Fragment() {

    var mUserDataBaseReference: DatabaseReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserDataBaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        usersfrag_recyclerview.setHasFixedSize(true)

        usersfrag_recyclerview.layoutManager = linearLayoutManager
        usersfrag_recyclerview.adapter = UsersAdapter(mUserDataBaseReference!!, context!!);

    }

}
