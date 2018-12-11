package app.com.foodorderapp.feature.details

import android.os.Bundle
import android.view.View
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_count_layout.*
import kotlinx.android.synthetic.main.item_details_activity.*

class ItemDetailsActivity : BaseActivity<ItemDetailsPresenter>(), ItemDetailsView {

    var foodItems:FoodItems? = null

    override fun onErrorMessage(resource: Int) {
        showToast(getString(resource))
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
        foodItems = bundle?.get(Constants.KEY_ITEM) as FoodItems

        image_add.setOnClickListener(clickListener)
        image_remove.setOnClickListener(clickListener)

        foodItems?.let {
            val itemCount = CartDao().getItemCount(it.item_name)
            Glide.with(this).load(it.image_url)
                    .apply(RequestOptions.centerCropTransform())
                    .into(details_image)
            text_item_name.text = it.item_name
            text_item_price.text = Constants.rupeesFormatter(it.item_price)
            text_rating.text = getString(R.string.rating_formatter, Constants
                    .rattingFormatter(it
                    .average_rating))
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
                image_add -> {
                    val updatedCount = count.plus(1)
                    presenter?.addToCart(it,updatedCount)
                    updateItemCount(it.item_name)
                    text_item_count.text = updatedCount.toString()
                }
                image_remove -> {
                    presenter?.handleRemoveItem(it)
                    updateItemCount(it.item_name)
                }

                else -> {

                }
            }
        }

    }

}