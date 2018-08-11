package spotter.netty.org.nettyspotter.ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import spotter.netty.org.nettyspotter.model.Netty

/**
 * Adapter for the list of public toilet.
 */
class CustomAdapter : PagedListAdapter<Netty, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NettyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as NettyViewHolder).bind(item)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Netty>() {
            override fun areItemsTheSame(oldItem: Netty, newItem: Netty): Boolean =
                    oldItem.objectId == newItem.objectId

            override fun areContentsTheSame(oldItem: Netty, newItem: Netty): Boolean =
                    oldItem == newItem
        }
    }
}