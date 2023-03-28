package com.example.donor.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.donor.R
import com.example.donor.data.RentInfo
import com.example.donor.ui.DonateActivity

class BorrowAdapter(var userList: ArrayList<RentInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rent_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userList[position]
        (holder as ViewHolder).bind(user)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            context.startActivity(Intent(context, DonateActivity::class.java))
        }
    }

    override fun getItemCount() = userList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userImg: ImageView = itemView.findViewById(R.id.item_img)
        private val username: TextView = itemView.findViewById(R.id.itemname)
        private val rent: TextView = itemView.findViewById(R.id.rent)

        fun bind(user: RentInfo) {
            // Load the user image using Glide
            Glide.with(itemView.context)
                .load(user.img)
                .into(userImg)
            username.text = user.name
            rent.text = user.rent + "rs"
        }
    }
}
