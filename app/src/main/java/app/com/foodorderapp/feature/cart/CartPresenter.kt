package app.com.foodorderapp.feature.cart

import app.com.foodorderapp.base.BasePresenter
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.model.realm.CartItem

class CartPresenter(view: CartView) : BasePresenter<CartView>(view) {

    fun getCartItems() {
        val cartList = getCartList()

        if(cartList.isEmpty()) {
            getView()?.onCartListEmpty()
        } else {
            getView()?.onCartListUpdated(cartList)
        }
    }

    fun processCouponCode(text: String, totalAmount: Float) {

        if(text.isNotEmpty()){

            when(text) {

                Constants.COUPON_1 -> {
                    if(totalAmount > 400){
                        getView()?.addTwentyPercentDiscount()
                    } else {
                        getView()?.onErrorMessage("Total Amount should be Greater than 400")
                    }
                }

                Constants.COUPON_2 -> {

                    if(totalAmount > 100){
                        getView()?.addFreeDelivery()
                    } else {
                        getView()?.onErrorMessage("Total Amount should be Greater than 100")
                    }
                }

                else -> {
                    getView()?.onErrorMessage("Invalid Coupon Code")
                }

            }

        } else {
            getView()?.onErrorMessage("Invalid Coupon Code")
        }
    }


    fun getDiscountedAmount(percentage: Int){

    }

    fun getTotalAmount(items: List<CartItem>) {

        var total: Float = 0f

        items.forEach {
            total = (total + (it.itemCount * it.itemPrice))
        }
        getView()?.setItemTotal(total)

    }

}
