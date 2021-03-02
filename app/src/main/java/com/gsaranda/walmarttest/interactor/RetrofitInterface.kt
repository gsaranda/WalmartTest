package com.gsaranda.walmarttest.interactor

import com.gsaranda.walmarttest.models.WrapperWalmartStoreModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("walmart-services/mg/address/storeLocatorCoordinates")
    suspend fun getStores() : Response<WrapperWalmartStoreModel>
}