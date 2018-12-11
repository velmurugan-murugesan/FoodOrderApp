package app.com.foodorderapp.feature.details

import android.os.Bundle
import android.view.View
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.feature.home.HomePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_details_activity.*

class ItemDetailsActivity : BaseActivity<ItemDetailsPresenter>(), ItemDetailsView {


    var foodItems:FoodItems? = null

    override fun onErrorMessage(s: String) {
        showToast(s)
    }

    var presenter: ItemDetailsPresenter? = null

    override fun getContentView(): Int {
        return R.layout.item_details_activity
    }

    override fun onCreatePresenter(): ItemDetailsPresenter? {
        return ItemDetailsPresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = getPresenter()
        val bundle = intent.extras
        foodItems = bundle?.get("item") as FoodItems

        image_add_count.setOnClickListener(clickListener)
        image_minus_count.setOnClickListener(clickListener)

        foodItems?.let {
            val itemCount = CartDao().getItemCount(it.item_name)
            Glide.with(this).load(it.image_url)
                    .apply(RequestOptions.centerCropTransform())
                    .into(details_image)
            text_item_name.text = it.item_name
            updateItemCount(it.item_name)

        }

    }


    private fun updateItemCount(itemName: String){
        val itemCount = CartDao().getItemCount(itemName)
        text_item_count.text = itemCount.toString()
    }

    private val clickListener = View.OnClickListener { view ->

        foodItems?.let {
            val count = presenter?.getItemCount(it.item_name)!!

            when(view){
                image_add_count -> {
                    val updatedCount = count.plus(1)
                    presenter?.addToCart(it,updatedCount)
                    updateItemCount(it.item_name)
                    text_item_count.text = updatedCount.toString()
                }
                image_minus_count -> {
                    handleRemoveItem(it)
                    updateItemCount(it.item_name)
                    //presenter?.addToCart(it,count.plus(1))
                }

                else -> {

                }
            }
        }

    }

}