package com.deniscosta.glub.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.deniscosta.glub.R
import com.deniscosta.glub.core.model.Offer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.*


class BottomSheetFragment : BottomSheetDialogFragment() {


    companion object{

        const val OFFER_KEY = "offer"

        fun newInstance(offer : Offer) : BottomSheetFragment{
            val bundle = Bundle()
            bundle.putParcelable(OFFER_KEY, offer)
            val fragment = BottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 1000

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.bottom_sheet_layout,container,false)
        bottomSheet = view.findViewById(R.id.bottomSheet)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offer = arguments?.getParcelable<Offer>(OFFER_KEY)

        val offerImage = view.findViewById<ImageView>(R.id.bs_offer_image)
        val offerTitle = view.findViewById<TextView>(R.id.bs_offer_title)
        val offerDescription = view.findViewById<TextView>(R.id.bs_offer_description)
        val offerPrice = view.findViewById<TextView>(R.id.bs_offer_price)

        if(offer?.images!!.isNotEmpty()){
            Glide.with(view)
                .asDrawable()
                .load(offer.images.first().image)
                .centerCrop()
                .into(offerImage)
        }

        offerTitle.text = offer.partner.name
        offerDescription.text = offer.description
        offerPrice.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(offer.price)

    }

    private fun setUpBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior
            .from(view!!.parent as View)
    }


}