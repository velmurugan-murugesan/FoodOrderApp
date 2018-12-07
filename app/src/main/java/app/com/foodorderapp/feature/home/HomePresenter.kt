package app.com.foodorderapp.feature.home

import android.util.Log
import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.data.service.ApiInterface
import io.reactivex.observers.DisposableSingleObserver

class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {


    fun getAllFoodItems(){
        val foodItems = ApiInterface.create().getFoodItems()
        subscribe(foodItems, object : DisposableSingleObserver<List<FoodItems>>() {
            override fun onSuccess(t: List<FoodItems>) {
                if (t.isNotEmpty()){
                    getView()?.updateFoodItems(t)
                } else {
                    getView()?.onEmptyItems("")
                }
            }

            override fun onError(e: Throwable) {
                e.message?.let {
                    Log.d("TAG",e.message)
                    getView()?.onErrorMessage(it)
                }
            }
        })
    }

}