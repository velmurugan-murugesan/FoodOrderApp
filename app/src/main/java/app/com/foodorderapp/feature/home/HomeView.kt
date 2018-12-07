package app.com.foodorderapp.feature.home

import app.com.foodorderapp.base.BaseView
import app.com.foodorderapp.data.model.FoodItems

interface HomeView : BaseView {

    fun onErrorMessage(message: String)
    fun onEmptyItems(message: String)
    fun updateFoodItems(foodItems: List<FoodItems>)
}