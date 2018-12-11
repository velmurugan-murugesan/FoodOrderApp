package app.com.foodorderapp.feature.cart

import app.com.foodorderapp.base.BaseView
import app.com.foodorderapp.data.model.realm.CartItem

interface CartView : BaseView {

    fun onCartListEmpty()
    fun onCartListUpdated(cartList: List<CartItem>)
    fun addTwentyPercentDiscount(discountAmount: Float, toPay: Float)
    fun addFreeDelivery()
    fun setItemTotal(total: Float)


}
