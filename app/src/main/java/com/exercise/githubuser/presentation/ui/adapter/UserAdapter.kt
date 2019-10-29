package com.exercise.githubuser.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.databinding.UserItemBinding

class UserAdapter : ListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallback())  {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        (holder as UserViewHolder).bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener{

            }
        }

        fun bind(item: User) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }
    }

    interface UserInfoListener {
        fun onUserClick(user: User)
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl
                    && oldItem.siteAdmin == newItem.siteAdmin
        }
    }
}