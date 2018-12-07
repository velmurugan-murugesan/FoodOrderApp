package app.com.foodorderapp.data.service

import app.com.foodorderapp.data.model.FoodItems
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("data.json")
    fun getFoodItems(): Single<List<FoodItems>>

    companion object {

        val BASE_URL = "https://android-full-time-task.firebaseio.com/"

        val defaultHttpClient = OkHttpClient.Builder()
                .connectTimeout(260, TimeUnit.SECONDS)
                .readTimeout(260, TimeUnit.SECONDS)
                .writeTimeout(260, TimeUnit.SECONDS)
                .build()

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(defaultHttpClient)
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}