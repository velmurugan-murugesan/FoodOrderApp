package app.com.foodorderapp.feature.cart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.model.realm.CartItem
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity<CartPresenter>(), CartView{

    override fun setItemTotal(total: Float) {
        itemTotal = total
        text_item_total.text = total.toString()
        text_delivery_charges.text = Constants.SYMBOL_RS + deliveryCharge
        text_to_pay.text = (total + deliveryCharge).toString()
    }

    override fun addTwentyPercentDiscount() {
        deliveryCharge = 30
        presenter?.calculateDiscount(itemTotal, 20)
    }

    override fun addFreeDelivery() {
        deliveryCharge = 0
        presenter?.getTotalAmount(adapter?.allItems!!)
    }

    private var presenter: CartPresenter? =null
    private var adapter: CartAdapter? = null
    private var itemTotal: Float = 0f
    private var deliveryCharge: Int = 30
    override fun getContentView(): Int {
        return R.layout.activity_cart
    }

    override fun onCreatePresenter(): CartPresenter? {
        return CartPresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = getPresenter()
        adapter = CartAdapter(this)
        recyclerview_cart.layoutManager = LinearLayoutManager(this)
        recyclerview_cart.adapter = adapter

        text_apply.setOnClickListener {it ->
            presenter?.processCouponCode(ed_apply_coupon.text.toString(), 200.toFloat())
        }

        presenter?.getCartItems()
    }

    override fun onCartListEmpty() {

    }

    override fun onCartListUpdated(cartList: List<CartItem>) {
        adapter?.setItems(cartList)
        presenter?.getTotalAmount(cartList)

    }

    private fun resetOffers(){
        deliveryCharge = 30
        presenter?.getTotalAmount(adapter?.allItems!!)
    }

    override fun onErrorMessage(s: String) {
        showToast(s)
        resetOffers()
    }

}