package com.gsaranda.walmarttest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.interactor.StoreLocatorInteractor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var storeLocatorInteractor: StoreLocatorInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storeLocatorInteractor= StoreLocatorInteractor(onSuccess = {
            tiendas->
            group_loading.visibility= View.GONE

        },onFailure = {


        })
    }

    override fun onUiReady() {
      storeLocatorInteractor.getWalmartStores()
    }
}
