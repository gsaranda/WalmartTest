package com.gsaranda.walmarttest.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.models.WalmartStoreModel

class StoreDetailsFragment():DialogFragment(){

  var  store:WalmartStoreModel?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mView = inflater.inflate(R.layout.dialog_fragment_store_detail, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(
            getDrawable(context!!,
                android.R.color.transparent
            )
        )
        return mView
    }


    //poner
    private fun dimenciona() {
        val displayMetrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val displayHeight = displayMetrics.heightPixels
        dialog?.window?.setLayout((displayWidth * .85).toInt(), (displayHeight * .6).toInt())
    }


}