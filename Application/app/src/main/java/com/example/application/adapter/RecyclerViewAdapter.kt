package com.example.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.data.UserInfo
import com.example.application.databinding.ItemsBinding

class RecyclerViewAdapter(private var list: List<UserInfo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemText: String, url: String) {
            binding.textView2.text = itemText
            Glide.with(binding.imageView.context)
                .load(url)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position].name, list[position].img)
    }

    fun setData(results: List<UserInfo>) {
        list = results
        notifyItemRangeChanged(0, list.size)
    }
}
