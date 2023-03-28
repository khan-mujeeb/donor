package com.example.donor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.donor.R
import com.example.donor.data.NGOInfo
import com.example.donor.ui.DonateActivity


class NGOAdapter(private val userList: List<NGOInfo>) : RecyclerView.Adapter<NGOAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ngo_listview, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            context.startActivity(Intent(context, DonateActivity::class.java))
        }
    }

    override fun getItemCount() = userList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userImg: ImageView = itemView.findViewById(R.id.user_img)
        private val username: TextView = itemView.findViewById(R.id.username)
        private val location: TextView = itemView.findViewById(R.id.location)
        private val description: TextView = itemView.findViewById(R.id.descrption)

        fun bind(user: NGOInfo) {
            // Load the user image using Glide
            Glide.with(itemView.context)
                .load(user.img)
                .into(userImg)
            username.text = user.name
            location.text = user.location
            description.text = user.description
        }
    }
}
