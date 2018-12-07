package app.com.foodorderapp.feature.cart

import android.os.Bundle
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.data.model.FoodItems
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity<CartPresenter>(){

    override fun getContentView(): Int {
        return R.layout.activity_cart
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setSupportActionBar(toolbar)

        val bundle = intent.extras
        val foodItems : FoodItems? = bundle?.get("item") as FoodItems

        foodItems?.let {
            Glide.with(this).load(foodItems.image_url)
                    .apply(RequestOptions.centerCropTransform())
                    .into(details_image)
            collapsingToolbar.title = foodItems.item_name
        }


    }

}