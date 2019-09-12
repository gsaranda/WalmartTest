package com.gsaranda.walmarttest.interactor

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.gsaranda.walmarttest.models.WalmartStoreModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StoreLocatorInteractor(
    val onSuccess: (List<WalmartStoreModel>) -> Unit,
    val onFailure: (errorType:ErrorTypes) -> Unit
) {


    val URL = "https://www.walmartmobile.com.mx/"
    var disposable: Disposable? = null

    fun getWalmartStores(context: Context) {
        val request = Retrofit.Builder()
            .baseUrl(URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitInterface::class.java)


        disposable =
            request.getStores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        // onSuccess(result.responseArray)
                        filterStoresbyRange(context, result.responseArray)
                    },
                    { error ->
                        Log.d("ERROR", error.message)
                        onFailure(ErrorTypes.CONNECTION)
                    }
                )
    }

    private fun filterStoresbyRange(context: Context, rawWalmartStores: List<WalmartStoreModel>) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            getNearbyStores(location, rawWalmartStores)
        }.addOnFailureListener {
            onFailure(ErrorTypes.GPS)
        }

    }

    private fun getNearbyStores(location: Location, rawWalmartStores: List<WalmartStoreModel>) {

//Primero se reduce el listado, de manera que al hacer el ordenamiento, ete sea lo mas rapido posible
        val storeIterator = rawWalmartStores.iterator() as MutableIterator<WalmartStoreModel>
        while (storeIterator.hasNext()) {
            val store = storeIterator.next()
            if (!isNearby(location, store)) {
                storeIterator.remove()
            }
        }
        val totalElementos=if(rawWalmartStores.size<25)  rawWalmartStores.size else 25

        //Se regresa la lista ya con los elementos ordenados
        onSuccess(rawWalmartStores.sortedBy {
            getDistance(
                location,
                it.latPoint.toDouble(),
                it.lonPoint.toDouble()
            )
        }.subList(0, totalElementos))
    }

    private fun isNearby(location: Location, store: WalmartStoreModel): Boolean {
        val storeLocation = Location(LocationManager.GPS_PROVIDER)
        storeLocation.latitude = store.latPoint.toDouble()
        storeLocation.longitude = store.lonPoint.toDouble()
        val distance = location.distanceTo(storeLocation)
        //preguntamos si esta a 200 km
        return distance < 100_000
    }

    private fun getDistance(
        location: Location,
        storeLatitud: Double,
        storeLongitud: Double
    ): Float {
        val storeLocation = Location(LocationManager.GPS_PROVIDER)
        storeLocation.latitude = storeLatitud
        storeLocation.longitude = storeLongitud
        val distance = location.distanceTo(storeLocation)
        return distance
    }

}