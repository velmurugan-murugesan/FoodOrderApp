package app.com.foodorderapp.feature.cart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.data.model.realm.CartItem
import app.com.foodorderapp.helper.FormatHelper
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity<CartPresenter>(), CartView {

    private var presenter: CartPresenter? = null
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

        text_apply.setOnClickListener { _ ->
            presenter?.processCouponCode(ed_apply_coupon.text.toString(), itemTotal)
        }

        presenter?.getCartItems()
    }

    override fun onCartListEmpty() {
        //
    }

    override fun onCartListUpdated(cartList: List<CartItem>) {
        adapter?.setItems(cartList)
        presenter?.getTotalAmount(cartList)
    }

    private fun resetOffers() {
        deliveryCharge = 30
        setItemTotal(itemTotal)
    }

    override fun onErrorMessage(resource: Int) {
        showToast(getString(resource))
        resetOffers()
    }

    override fun addTwentyPercentDiscount(discountAmount: Float, toPay: Float) {
        deliveryCharge = 30
        setBillDetails(itemTotal, discountAmount)
        val string = getString(R.string.rupees_formatter, FormatHelper.priceDecimalFormatter(discountAmount))
        text_discount_amount.text = getString(R.string.discount_formatter,string)
        text_discount_amount.visibility = VISIBLE
        discount_amount.visibility = VISIBLE
    }

    override fun setItemTotal(total: Float) {
        itemTotal = total
        setBillDetails(total, 0f)
        text_discount_amount.visibility = GONE
        discount_amount.visibility = GONE
    }

    private fun setBillDetails(sum: Float, discount: Float) {
        text_item_total.text = getString(R.string.rupees_formatter, FormatHelper
                .priceDecimalFormatter(sum))
        text_delivery_charges.text = getString(R.string.rupees_formatter, FormatHelper
                .priceDecimalFormatter(deliveryCharge.toFloat()))
        text_to_pay.text = getString(R.string.rupees_formatter, FormatHelper.priceDecimalFormatter((sum - discount) +
                deliveryCharge))
    }

    override fun addFreeDelivery() {
        deliveryCharge = 0
        setItemTotal(itemTotal)
    }

}