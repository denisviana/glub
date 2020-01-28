package com.deniscosta.glub.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deniscosta.glub.R
import com.deniscosta.glub.core.model.Offer
import com.deniscosta.glub.ui.common.EndlessRecyclerViewScrollListener
import com.deniscosta.glub.ui.main.adapter.OfferListAdapter
import com.deniscosta.glub.ui.main.adapter.OnChildClickListener
import com.deniscosta.glub.ui.main.fragment.BottomSheetFragment
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class OfferListActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context : Context) = Intent(context, OfferListActivity::class.java)
    }

    private val mViewModel : OfferListViewModel by inject()
    private lateinit var mOfferListAdapter : OfferListAdapter
    private val mPermissionCodeRequest = 133
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback
    private var mLocationRequest : LocationRequest ? = null
    private var mCurrentLocation : Location ? =  null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        mViewModel.event(CheckLocationPermissions())

        mViewModel.state.observe(this, Observer {
            handleState(it)
        })

    }


    private fun configureLocations(){

        mLocationRequest = LocationRequest.create()?.apply {
            interval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                this.mCurrentLocation = location
            }

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    mCurrentLocation = location
                    mCurrentLocation?.let {
                        mViewModel.event(RefreshOfferList(it))

                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback, null)
    }

    private fun initViews(){

        mOfferListAdapter = OfferListAdapter(offers = mutableListOf())
        val mLayoutManager =  LinearLayoutManager(this@OfferListActivity, LinearLayoutManager.VERTICAL, false)

        rv_offers.apply {
            layoutManager = mLayoutManager
            adapter = mOfferListAdapter
            setHasFixedSize(true)
        }

        rv_offers.addOnScrollListener(object : EndlessRecyclerViewScrollListener(mLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

            }

        })

        mOfferListAdapter.addOnClickListener(object : OnChildClickListener{
            override fun onChildClick(offer: Offer) {
                mViewModel.event(OnClickOffer(offer))
            }
        }
        )


        swipe_refresh.setOnRefreshListener {
            mCurrentLocation?.let {
                mViewModel.event(RefreshOfferList(it))
            }
        }


    }

    private fun handleState(state : OfferState){

        when(state){
            is LoadingOffers -> {
                loading_offers.visibility = VISIBLE
            }
            is OfferLoadingSuccessful -> {
                loading_offers.visibility = GONE
                if(swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
                mOfferListAdapter.addItems(state.offers)
            }
            is OfferLoadingFailed -> {
                if(swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
                loading_offers.visibility = GONE
            }
            is LocationPermissions -> {
                checkLocationPermissions()
            }
            is ShowOfferDetails -> {
                BottomSheetFragment
                    .newInstance(state.offer)
                    .show(supportFragmentManager.beginTransaction(),"bottomsheet")
            }
        }

    }

    private fun checkLocationPermissions(){

        val mPermissionAccessLocationApproved = ActivityCompat
            .checkSelfPermission(this, ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat
            .checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

        if (mPermissionAccessLocationApproved) {
           configureLocations()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
                mPermissionCodeRequest
            )
        }
    }
}
