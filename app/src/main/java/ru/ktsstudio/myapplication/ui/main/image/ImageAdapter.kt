package ru.ktsstudio.myapplication.ui.main.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.*
import ru.ktsstudio.myapplication.R

class ImageAdapter : ListAdapter<String, ImageAdapter.Holder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false).let(::Holder)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        fun bind(image: String) {
            Glide.with(imageView)
                .load(image)
                .placeholder(R.drawable.bg_placeholder)
                .error(R.drawable.bg_placeholder)
                .transition(withCrossFade(factory))
                .into(imageView)
        }
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
}