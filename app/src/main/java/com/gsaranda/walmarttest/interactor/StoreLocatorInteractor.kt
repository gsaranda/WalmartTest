package com.gsaranda.walmarttest.interactor

import android.util.Log
import com.gsaranda.walmarttest.models.WalmartStoreModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StoreLocatorInteractor(val onSuccess: (List<WalmartStoreModel>) -> Unit, val onFailure: () -> Unit) {


    val URL = "https://www.walmartmobile.com.mx/"
    var disposable: Disposable? = null

    fun getWalmartStores() {
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
                        Log.d("EXITO", result.responseArray.get(0).address)
                        onSuccess(result.responseArray)
                    },
                    { error ->
                        Log.d("ERROR", error.message)
                        onFailure()
                    }
                )
    }


}