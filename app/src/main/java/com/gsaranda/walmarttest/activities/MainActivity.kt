package com.gsaranda.walmarttest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.interactor.StoreLocatorInteractor

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onUiReady() {
       StoreLocatorInteractor().getWalmartStores()
    }
}
