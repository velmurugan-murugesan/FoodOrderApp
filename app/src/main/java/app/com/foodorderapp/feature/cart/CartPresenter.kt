package app.com.foodorderapp.feature.cart

import app.com.foodorderapp.R.string
import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.realm.CartItem

class CartPresenter(view: CartView) : BasePresenter<CartView>(view) {

    fun getCartItems() {
        val cartList = getCartList()

        if (cartList.isEmpty()) {
            getView()?.onCartListEmpty()
        } else {
            getView()?.onCartListUpdated(cartList)
        }
    }

    private fun getCartList(): List<CartItem> {
        return CartDao().getCartItems(CartItem::class.java)
    }

    fun processCouponCode(text: String, totalAmount: Float) {

        if (text.isNotEmpty()) {

            when (text) {
                Constants.COUPON_1 -> {
                    if (totalAmount > 400) {
                        calculateDiscount(totalAmount, 20)
                    } else {
                        getView()?.onErrorMessage(string.amount_should_be_400)
                    }
                }

                Constants.COUPON_2 -> {

                    if (totalAmount > 100) {
                        getView()?.addFreeDelivery()
                    } else {
                        getView()?.onErrorMessage(string.coupon_2_error_message)
                    }
                }

                else -> {
                    getView()?.onErrorMessage(string.invalid_coupon_code)
                }

            }

        } else {
            getView()?.onErrorMessage(string.invalid_coupon_code)
        }
    }

    fun getTotalAmount(items: List<CartItem>) {

        var total: Float = 0f
        items.forEach {
            total = (total + (it.itemCount * it.itemPrice))
        }
        getView()?.setItemTotal(total)

    }

    private fun calculateDiscount(itemTotal: Float, i: Int) {
        val discount = (i * itemTotal) / 100
        getView()?.addTwentyPercentDiscount(discount, (itemTotal - discount))
    }

}
