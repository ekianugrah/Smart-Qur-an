package com.ekianugrah.myproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ekianugrah.myproject.R
import com.ekianugrah.myproject.model.ResponseMain
import kotlinx.android.synthetic.main.kisah_list.view.*

class KisahAdapter(
    val data: List<ResponseMain?>,
    val itemClick: OnClickListener
) : RecyclerView.Adapter<KisahAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kisah_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item!!)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ResponseMain) {
            view.item_name.text = item.name

            Glide.with(view.context)
                .load(item.imageUrl)
                .apply(RequestOptions())
                .override(500, 500)
                .placeholder(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(view.item_img)

            view.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun detail(item: ResponseMain)
    }
}