package com.gsaranda.walmarttest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.adapter.WalmartStoreRecyclerViewAdapter
import com.gsaranda.walmarttest.fragments.StoreDetailsFragment
import com.gsaranda.walmarttest.interactor.StoreLocatorInteractor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var storeLocatorInteractor: StoreLocatorInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_tiendas_cercanas.layoutManager=LinearLayoutManager(this)
        val recyclerViewAdapter=WalmartStoreRecyclerViewAdapter(onItemSelected = {
            store->
            val storeDetailsFragment=StoreDetailsFragment()
            storeDetailsFragment.store=store
            val fragmentDuplicate=supportFragmentManager.findFragmentByTag(getString(R.string.tag_store_detail_fragment))
            if(fragmentDuplicate==null) {
                storeDetailsFragment.show(supportFragmentManager,getString(R.string.tag_store_detail_fragment))
            }
        })
        rv_tiendas_cercanas.adapter=recyclerViewAdapter
        storeLocatorInteractor= StoreLocatorInteractor(onSuccess = {
            tiendasFound->
            group_loading.visibility= View.GONE
            recyclerViewAdapter.tiendas=tiendasFound
            recyclerViewAdapter.notifyDataSetChanged()

        },onFailure = {


        })
    }

    override fun onUiReady() {
      storeLocatorInteractor.getWalmartStores(this)
    }
}
