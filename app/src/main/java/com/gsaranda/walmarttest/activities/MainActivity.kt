package com.gsaranda.walmarttest.activities

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.adapter.WalmartStoreRecyclerViewAdapter
import com.gsaranda.walmarttest.fragments.StoreDetailsFragment
import com.gsaranda.walmarttest.interactor.ErrorTypes
import com.gsaranda.walmarttest.interactor.StoreLocatorInteractor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var storeLocatorInteractor: StoreLocatorInteractor
    val GPS_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_tiendas_cercanas.layoutManager = LinearLayoutManager(this)
        val recyclerViewAdapter = WalmartStoreRecyclerViewAdapter(onItemSelected = { store ->
            val storeDetailsFragment = StoreDetailsFragment()
            storeDetailsFragment.store = store
            val fragmentDuplicate =
                supportFragmentManager.findFragmentByTag(getString(R.string.tag_store_detail_fragment))
            if (fragmentDuplicate == null) {
                storeDetailsFragment.show(
                    supportFragmentManager,
                    getString(R.string.tag_store_detail_fragment)
                )
            }
        })
        rv_tiendas_cercanas.adapter = recyclerViewAdapter
        storeLocatorInteractor = StoreLocatorInteractor(onSuccess = { tiendasFound ->
            group_loading.visibility = View.GONE
            recyclerViewAdapter.tiendas = tiendasFound
            recyclerViewAdapter.notifyDataSetChanged()

        }, onFailure = { errorType ->
            group_loading.visibility = View.GONE

            val message = getErrorMessage(errorType)
            despliegaModal(message,
                getString(R.string.modal_retry_button),
                aceptCallback = { dialog, id ->
                    makeStoresLocationRequest()
                    dialog.dismiss()
                },
                cancelCallback = { dialog: DialogInterface, id: Int ->
                    dialog.cancel()
                    finish()
                }
            )


        })
    }

    override fun onUiReady() {
        if (userHaveGpsPermissions()) {
            storeLocatorInteractor.getWalmartStores(this)
        } else {
            group_loading.visibility = View.GONE
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            GPS_PERMISSION_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                            despliegaModal(getString(R.string.error_need_gps_permissions),
                                getString(R.string.modal_accept),
                                aceptCallback = { dialog, id ->
                                    dialog.dismiss()
                                    finish()
                                })
                        }
                        else {
                            despliegaModal(getString(R.string.error_explain_gps_permissions),
                                getString(R.string.modal_retry_button),
                                aceptCallback = { dialog, id ->
                                    requestPermission()
                                    dialog.dismiss()
                                },
                                cancelCallback = { dialog: DialogInterface, id: Int ->
                                    dialog.cancel()
                                    finish()
                                })
                        }

                } else {
                    makeStoresLocationRequest()
                }

            }
        }
    }

    private fun getErrorMessage(errorType: ErrorTypes): String {

        when (errorType) {
            ErrorTypes.CONNECTION -> return getString(R.string.error_message_internet)
            ErrorTypes.GPS -> return getString(R.string.error_message_gps)

            else -> return ""
        }
    }

    private fun userHaveGpsPermissions(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            GPS_PERMISSION_CODE
        )
    }



    private fun makeStoresLocationRequest() {
        group_loading.visibility = View.VISIBLE
        storeLocatorInteractor.getWalmartStores(this)
    }
}
