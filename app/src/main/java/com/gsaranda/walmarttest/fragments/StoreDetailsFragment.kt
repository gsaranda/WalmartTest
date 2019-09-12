package com.gsaranda.walmarttest.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.gsaranda.walmarttest.models.WalmartStoreModel
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gsaranda.walmarttest.R
import kotlinx.android.synthetic.main.dialog_fragment_store_detail.*


class StoreDetailsFragment() : DialogFragment() {


    var store: WalmartStoreModel? = null
    var onPause = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mView = inflater.inflate(R.layout.dialog_fragment_store_detail, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(
            getDrawable(
                context!!,
                android.R.color.transparent
            )
        )
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun showStoreOnMap() {
        val mapfragment = mapa as SupportMapFragment
        mapfragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap?) {

                val latLng = LatLng(store?.latPoint!!.toDouble(), store?.lonPoint!!.toDouble())


                val markerOptions: MarkerOptions =
                    MarkerOptions().position(latLng).title(store?.name)


                val zoomLevel = 18.0f

                googleMap.let {
                    it!!.addMarker(markerOptions).showInfoWindow()
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (onPause) {
            onPause = false
        } else {
            dimenciona()
            if(store?.manager!=""){
                tv_detail_manager_value.text=store?.manager
            }
            else{
                tv_detail_manager_value.visibility=View.GONE
                tv_detail_manager_label.visibility=View.GONE
            }

            if(store?.telephone!="")
            {
                tv_detail_phone_value.text=store?.telephone
            }
            else{
                tv_detail_phone_value.visibility=View.GONE
                tv_detail_phone_label.visibility=View.GONE
            }


            tv_detail_open_value.text=store?.opens

            showStoreOnMap()
        }
    }


    override fun onPause() {
        super.onPause()
        onPause = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fragment = fragmentManager!!
            .findFragmentById(R.id.mapa)
        if (fragment != null)
            fragmentManager!!.beginTransaction().remove(fragment).commit()
    }

    private fun dimenciona() {
        val displayMetrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val displayHeight = displayMetrics.heightPixels
        dialog?.window?.setLayout(displayWidth, displayHeight)
    }


}