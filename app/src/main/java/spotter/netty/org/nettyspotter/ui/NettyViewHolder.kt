package spotter.netty.org.nettyspotter.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import spotter.netty.org.nettyspotter.R
import spotter.netty.org.nettyspotter.model.Netty

/**
 * View Holder for a [Netty] RecyclerView list item.
 */
class NettyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val district: TextView = view.findViewById(R.id.districtView)
    private val address: TextView = view.findViewById(R.id.completeAddressView)
    private val openHours: TextView = view.findViewById(R.id.openHoursView)

    private var netty: Netty? = null

    init {
        view.setOnClickListener {
            Log.d("NettyViewHolder", "Clicked ${netty?.objectId}")
        }
    }

    fun bind(netty: Netty?) {
        if (netty == null) {
            val resources = itemView.resources
            district.text = resources.getString(R.string.loading)
            address.text = resources.getString(R.string.loading)
            openHours.text = resources.getString(R.string.unknown)
        } else {
            showData(netty)
        }
    }

    private fun showData(netty: Netty) {
        this.netty = netty

        district.text = this.address.context.getString(R.string.district, netty.district)
        address.text = this.address.context.getString(R.string.complete_address, netty.number, netty.streetName)
        openHours.text = netty.openHours
    }

    companion object {
        fun create(parent: ViewGroup): NettyViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_view_item, parent, false)
            return NettyViewHolder(view)
        }
    }
}