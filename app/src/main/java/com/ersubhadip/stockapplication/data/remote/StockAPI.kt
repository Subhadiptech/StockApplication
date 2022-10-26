package com.ersubhadip.stockapplication.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apiKey") key:String
    ):ResponseBody

    companion object{
        const val API_KEY="TO8XG1N4GD36301B"
        const val BASE_URL="alphavantage.co"
    }


}