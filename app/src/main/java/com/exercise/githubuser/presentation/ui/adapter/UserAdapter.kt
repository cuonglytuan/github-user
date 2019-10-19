package com.exercise.githubuser.presentation.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exercise.githubuser.R
import com.exercise.githubuser.data.entity.User
import com.squareup.picasso.Picasso
import java.util.*

class UserAdapter(
    private val context: Context,
    private var list: ArrayList<User>,
    private val listener: UserInfoListener?
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun update(itemUser: List<User>) {
        this.list.addAll(itemUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (itemCount <= 0) return
        val user: User = list[position]

        holder.textViewName.text = user.login
        when (user.siteAdmin) {
            true -> holder.textViewBadge.visibility = View.VISIBLE
            else -> holder.textViewBadge.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(user.avatarUrl)) {
            Picasso.get().load(user.avatarUrl).into(holder.imageViewUser)
        }
        holder.linearLayoutContainer.setOnClickListener {
            listener?.onUserClick(user)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayoutContainer: LinearLayout = itemView.findViewById(R.id.linearLayoutContainer)
        var imageViewUser: ImageView = itemView.findViewById(R.id.imageViewUser)
        var textViewName: TextView = itemView.findViewById(R.id.textViewName)
        var textViewBadge: TextView = itemView.findViewById(R.id.textViewBadge)
    }

    interface UserInfoListener {
        fun onUserClick(user: User)
    }
}