package com.genius.amateur.whatschat.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.genius.amateur.whatschat.R
import com.genius.amateur.whatschat.models.FriendlyMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    var mFirebaseUser: FirebaseUser? = null
    var mFirebaseDatabase: DatabaseReference? = null
    var userId: String? = null

    var mLinaerLayoutManager: LinearLayoutManager? = null
    var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mFirebaseUser = FirebaseAuth.getInstance().currentUser
        if (intent.extras != null) {
            userId = intent.extras.getString("userId")
            mLinaerLayoutManager = LinearLayoutManager(this)
            mLinaerLayoutManager!!.stackFromEnd = true
        }
        supportActionBar?.title = "Chat Box"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mFirebaseDatabase = FirebaseDatabase.getInstance().reference

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(FriendlyMessage::class.java, R.layout.item_message, MessageViewHolder::class.java, mFirebaseDatabase!!.child("message")) {
            @SuppressLint("RtlHardcoded")
            override fun populateViewHolder(viewHolder: MessageViewHolder?, friendlyMessage: FriendlyMessage?, position: Int) {

                if (friendlyMessage != null) {
                    viewHolder!!.bind(friendlyMessage)

                    var currentUserId = mFirebaseUser!!.uid

                    var isMe: Boolean = friendlyMessage!!.id!!.equals(currentUserId)

                    if (isMe) {
                        // ME right side, My messages will be visible here
                        viewHolder.profileImageView!!.visibility = View.GONE
                        viewHolder.profileImageViewRight!!.visibility = View.VISIBLE

                        viewHolder.messageTextView!!.gravity = (Gravity.RIGHT or Gravity.CENTER_VERTICAL)
                        viewHolder.messengerTextView!!.gravity = (Gravity.RIGHT or Gravity.CENTER_VERTICAL)

                        //Get image url for me
                        mFirebaseDatabase!!.child("Users").child(currentUserId).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var imageUrl = dataSnapshot.child("thumb_image").value.toString()
                                var displayName = dataSnapshot.child("display_name").value.toString()

                                viewHolder.messengerTextView!!.text ="I wrote.."
                                Picasso.get().load(imageUrl.toString()).placeholder(R.drawable.profile_img).into(viewHolder.profileImageViewRight)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })

                    } else {

                        viewHolder.profileImageView!!.visibility = View.VISIBLE
                        viewHolder.profileImageViewRight!!.visibility = View.GONE

                        viewHolder.messageTextView!!.gravity = (Gravity.START or Gravity.CENTER_VERTICAL)
                        viewHolder.messengerTextView!!.gravity = (Gravity.START or Gravity.CENTER_VERTICAL)

                        //Get image url for me
                        mFirebaseDatabase!!.child("Users").child(userId).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var imageUrl = dataSnapshot.child("thumb_image").value.toString()
                                var displayName = dataSnapshot.child("display_name").value.toString()

                                viewHolder.messengerTextView!!.text = "$displayName" + " wrote..."
                                Picasso.get().load(imageUrl.toString()).placeholder(R.drawable.profile_img).into(viewHolder.profileImageView)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                            }

                        })


                    }
                }
            }
        }

        messageRecyclerView.layoutManager = mLinaerLayoutManager
        messageRecyclerView.adapter = mFirebaseAdapter

        sendButton.setOnClickListener {
            if (!intent.extras.get("userName").toString().equals("")) {
                var currentUserName = intent.extras.get("userName").toString()
                var currentUserId = mFirebaseUser!!.uid

                var friendlyMessage = FriendlyMessage(currentUserId, messageEdt.text.toString().trim(), currentUserName.trim())

                mFirebaseDatabase!!.child("message").push().setValue(friendlyMessage)

                messageEdt.setText("")
            }
        }

    }


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageTextView: TextView? = null
        var messengerTextView: TextView? = null
        var profileImageView: CircleImageView? = null
        var profileImageViewRight: CircleImageView? = null


        fun bind(friendlyMessage: FriendlyMessage) {
            messageTextView = itemView.findViewById(R.id.messageTextview)
            messengerTextView = itemView.findViewById(R.id.messengerTextview)
            profileImageView = itemView.findViewById(R.id.messengerImageView);
            profileImageViewRight = itemView.findViewById(R.id.messengerImageViewRight);


            messengerTextView?.text = friendlyMessage.name
            messageTextView?.text = friendlyMessage.text
        }
    }

}
