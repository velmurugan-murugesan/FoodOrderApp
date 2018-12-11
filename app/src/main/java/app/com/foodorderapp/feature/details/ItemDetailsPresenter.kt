package app.com.foodorderapp.feature.details

import android.util.Log
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.data.model.realm.CartItem

class ItemDetailsPresenter(view: ItemDetailsView) : BasePresenter<ItemDetailsView>(view) {
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