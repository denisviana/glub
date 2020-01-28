package com.deniscosta.glub.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deniscosta.glub.R
import com.deniscosta.glub.core.model.Offer
import kotlinx.android.synthetic.main.layout_item_list.view.*
import java.text.NumberFormat
import java.util.*

class OfferListAdapter(
    private val offers : MutableList<Offer>
) : RecyclerView.Adapter<OfferListAdapter.OfferListViewHolder>(){

    var onChildClickListener : OnChildClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_list, parent, false)
        return OfferListViewHolder(view)
    }

    override fun getItemCount(): Int = offers.size

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        holder.bind(offers[position], onChildClickListener)
    }

    fun addItems(offers : List<Offer>) {
        this.offers.clear()
        this.offers.addAll(offers)
        notifyDataSetChanged()
    }

    fun addOnClickListener(onChildClickListener : OnChildClickListener){
        this.onChildClickListener = onChildClickListener
    }

    class OfferListViewHolder( itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val offerTitle  = itemView.offer_title
        private val price = itemView.offer_price
        private val image = itemView.offer_thumbnail
        private val offerItemLayout = itemView.offer_item_layout

        fun bind(offer: Offer, onChildClickListener: OnChildClickListener ?) {
            offerTitle.text = offer.partner.name
            price.text = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(offer.price)
            Glide.with(itemView)
                .asDrawable()
                .load(offer.images[0].thumb)
                .centerCrop()
                .into(image)

            onChildClickListener?.let {
                offerItemLayout.setOnClickListener {
                    onChildClickListener.onChildClick(offer)
                }
            }
        }


    }

}

interface OnChildClickListener{
    fun onChildClick(offer : Offer)
}