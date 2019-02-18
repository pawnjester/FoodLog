package com.andela.logfooddiary.ui.photoUpload

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.andela.logfooddiary.R
import com.andela.logfooddiary.data.Upload
import com.andela.logfooddiary.databinding.SinglePhotoBinding
import com.squareup.picasso.Picasso


class PhotoDiaryAdapter: RecyclerView.Adapter<PhotoDiaryAdapter.PhotodiaryViewHolder>() {

    private val photos = mutableListOf<Upload>()

    fun updateList(update: List<Upload>) {
        photos.clear()
        photos.addAll(update)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PhotodiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SinglePhotoBinding>(
                inflater, R.layout.single_photo, parent, false
        )
        return PhotodiaryViewHolder(binding)
    }

    override fun getItemCount(): Int  = photos.size

    override fun onBindViewHolder(holder: PhotodiaryViewHolder, position: Int) {
        val item = photos[position]
        Picasso.get().load(item.imageUrl)
                .fit()
                .centerCrop()
                .into(holder.binding.photoImage)
        holder.bind(item)
    }

    inner class PhotodiaryViewHolder(val binding: SinglePhotoBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Upload) {
            binding.item = item
        }
    }
}