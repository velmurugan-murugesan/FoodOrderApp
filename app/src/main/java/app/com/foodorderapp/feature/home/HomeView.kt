package app.com.foodorderapp.feature.home

import app.com.foodorderapp.base.BaseView
import app.com.foodorderapp.data.model.FoodItems

interface HomeView : BaseView {

    fun onEmptyItems(message: String)
    fun updateFoodItems(foodItems: List<FoodItems>)
    fun updateBottomCart(totalCount: Int, totalPrice: Float)
}