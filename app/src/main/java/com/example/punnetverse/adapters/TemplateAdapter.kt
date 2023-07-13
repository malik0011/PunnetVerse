package com.example.punnetverse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.punnetverse.data.Template
import com.example.punnetverse.databinding.ItemTempBinding



class TemplateAdapter(private val mList: List<Template>) : RecyclerView.Adapter<TemplateAdapter.TempViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
        val binding = ItemTempBinding.inflate(LayoutInflater.from(parent.context))
        val height: Int =parent.measuredHeight / 4
        binding.img.minimumHeight = height
        return TempViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
        holder.setData(mList[position])
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class TempViewHolder(val binding: ItemTempBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(tempData: Template) {
            binding.img.setImageResource(tempData.imgSource)
        }
    }
}
