package app.com.foodorderapp.feature.home

import android.util.Log
import android.view.View
import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.data.model.realm.CartItem
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

    fun updateBottomCart() {

        val cartList = getCartList()

        var totalCount: Int = 0
        var totalPrice: Float = 0f

        cartList.forEach {

            totalCount = totalCount + it.itemCount
            totalPrice = totalPrice + (it.itemCount * it.itemPrice)

        }

        getView()?.updateBottomCart(totalCount,totalPrice)







    }

}