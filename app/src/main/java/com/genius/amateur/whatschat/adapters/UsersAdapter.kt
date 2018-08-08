package com.genius.amateur.whatschat.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.genius.amateur.whatschat.R
import com.genius.amateur.whatschat.activities.ChatActivity
import com.genius.amateur.whatschat.activities.ProfileActivity
import com.genius.amateur.whatschat.models.Users
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView




class UsersAdapter(databaseQuery: DatabaseReference, var context: Context) : FirebaseRecyclerAdapter<Users, UsersAdapter.ViewHolder>(Users::class.java, R.layout.users_row_layout, UsersAdapter.ViewHolder::class.java, databaseQuery

) {
    override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: Users?, position: Int) {
        var userId = getRef(position).key //unique keyID for this currentUser

        viewHolder!!.bind(user!!, context);
        viewHolder.userProfilePic!!.setOnClickListener {
            var intent = Intent(context,ProfileActivity::class.java)
            intent.putExtra("userId",userId)
            context.startActivity(intent)
        }

        viewHolder.itemView!!.setOnClickListener {
            var intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("userName",viewHolder.userNameTxt)
            intent.putExtra("status",viewHolder.userStatusTxt)
            intent.putExtra("profilepic",viewHolder.userProfilePicLink)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userNameTxt: String? = null
        var userStatusTxt: String? = null
        var userProfilePicLink: String? = null
        var userProfilePic : CircleImageView? = null

        fun bind(user: Users, context: Context) {
            var userName = itemView.findViewById<TextView>(R.id.userName)
            var userStatus = itemView.findViewById<TextView>(R.id.userStatus)
             userProfilePic = itemView.findViewById<CircleImageView>(R.id.usersProfile)

            //Set the string so that we can pass them in the intent

            userNameTxt = user.display_name;
            userStatusTxt = user.status;
            userProfilePicLink = user.thumb_image

            userName.text = userNameTxt
            userStatus.text = userStatusTxt

            Picasso.get().load(userProfilePicLink).placeholder(R.drawable.profile_img).into(userProfilePic)

        }
    }

}