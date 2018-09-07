package com.example.kevinlay.planr.ui.home

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.model.User

class UsersAdapter: RecyclerView.Adapter<UserViewHolder>() {
    var users: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_user_item_list, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val bitmap = BitmapFactory.decodeResource(holder.view.resources, R.drawable.ic_menu)
//        holder.userImage.setImageBitmap(bitmap)
        Glide.with(holder.view).load("https://cdn0.iconfinder.com/data/icons/social-messaging-ui-color-shapes/128/user-male-circle-blue-512.png").apply(RequestOptions.circleCropTransform()).into(holder.userImage)
//        Glide.with(holder.view).load(users[position].userImage).into(holder.userImage)
    }
}


class UserViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val userImage = view.findViewById<ImageView>(R.id.usersImage)
}