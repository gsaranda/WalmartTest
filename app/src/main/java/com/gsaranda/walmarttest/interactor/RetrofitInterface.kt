package com.gsaranda.walmarttest.interactor

import com.gsaranda.walmarttest.models.WalmartStoreModel
import com.gsaranda.walmarttest.models.WrapperWalmartStoreModel
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("walmart-services/mg/address/storeLocatorCoordinates")
    fun getStores() : Observable<WrapperWalmartStoreModel>
}