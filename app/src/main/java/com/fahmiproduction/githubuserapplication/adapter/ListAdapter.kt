package com.fahmiproduction.githubuserapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahmiproduction.githubuserapplication.databinding.ItemUserBinding
import com.fahmiproduction.githubuserapplication.datamodel.UserResponse

class ListAdapter(private val listUser: List<UserResponse>) :
    RecyclerView.Adapter<ListAdapter.ListUserViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var binding: ItemUserBinding

    class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListUserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        listUser[position].let { user ->
            holder.apply {
                binding.itemTitle.text = user.getAtUsername()
                binding.itemSubtitle.text = user.type
                Glide.with(holder.itemView.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.avatarImg)
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(user, position) }
            }
        }
    }

    override fun getItemCount(): Int = listUser.size
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(itemResponse: UserResponse, position: Int)
    }
}