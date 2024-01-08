package com.anhpt.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// UserAdapter.kt
// Import các thư viện cần thiết

class UserAdapter(private val users: List<User>, private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    // ViewHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(holder.itemView).load(user.avatar.thumbnail).into(holder.thumbnail)

        // Set thông tin cho view holder
        holder.nameTextView.text = user.name
        holder.emailTextView.text = user.email
        holder.addressTextView.text = "${user.address.street}, ${user.address.city}"
        // Load ảnh thumbnail bằng Glide hoặc thư viện tương tự
        // Glide.with(holder.itemView).load(user.thumbnailUrl).into(holder.thumbnail)

        // Xử lý sự kiện khi một phần tử được nhấn
        holder.itemView.setOnClickListener {
            onItemClick.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    // ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }
}
