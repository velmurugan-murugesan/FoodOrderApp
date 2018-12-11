package app.com.foodorderapp.feature.home

import android.util.Log
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.data.model.realm.CartItem
import app.com.foodorderapp.data.service.ApiInterface
import io.reactivex.observers.DisposableSingleObserver

class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {

    fun getAllFoodItems() {
        val foodItems = ApiInterface.create().getFoodItems()
        subscribe(foodItems, object : DisposableSingleObserver<List<FoodItems>>() {
            override fun onSuccess(t: List<FoodItems>) {
                if (t.isNotEmpty()) {
                    getView()?.updateFoodItems(t)
                } else {
                    getView()?.onEmptyItems("")
                }
            }

            override fun onError(e: Throwable) {
                e.message?.let {
                    Log.d("getAllFoodItems", e.toString())
                    getView()?.onErrorMessage(R.string.error_getting_food_items)
                }
            }
        })
    }

    fun addToCart(foodItem: FoodItems?, count: Int) {

        foodItem?.let { it ->
            val cartItem = CartItem()
            cartItem.itemName = it.item_name
            cartItem.itemImage = it.image_url
            cartItem.itemPrice = it.item_price
            cartItem.itemRating = it.average_rating
            cartItem.itemCount = count

            CartDao().storeOrUpdateCart(cartItem, object : DaoResponse<CartItem> {
                override fun onSuccess(message: CartItem?) {
                    // No Operation for Now
                    updateBottomCart()
                }

                override fun onFailure(errorMessage: String?) {
                    Log.d("addToCart", errorMessage)
                }
            })
        }
    }

    fun getItemCount(itemName: String): Int {
        return CartDao().getItemCount(itemName)
    }

    fun deleteCart(itemName: String) {
        CartDao().deleteByKey(CartItem::class.java, Constants.KEY_ITEMNAME, itemName)
    }

    fun getCartList(): List<CartItem> {
        return CartDao().getCartItems(CartItem::class.java)
    }

    fun updateBottomCart() {
        val cartList = getCartList()
        var totalCount: Int = 0
        var totalPrice: Float = 0f

        cartList.forEach {
            totalCount += it.itemCount
            totalPrice += (it.itemCount * it.itemPrice)
        }

        getView()?.updateBottomCart(totalCount, totalPrice)
    }

    fun handleRemoveItem(model: FoodItems) {
        val count: Int = getItemCount(model.item_name)

        when (count) {
            0 -> {
                getView()?.onErrorMessage(R.string.add_items_to_cart)
            }

            1 -> {
                deleteCart(model.item_name)
            }

            else -> {
                addToCart(model, count.minus(1))
            }
        }
    }

}