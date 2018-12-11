package app.com.foodorderapp.feature.home

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.callback.OnListItemClickListener
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.feature.cart.CartActivity
import app.com.foodorderapp.feature.details.ItemDetailsActivity
import app.com.foodorderapp.helper.CompareObjects
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_cart_layout.*
import android.util.Pair as UtilPair


class HomeActivity : BaseActivity<HomePresenter>(), HomeView {
    override fun updateBottomCart(totalCount: Int, totalPrice: Float) {
        text_cart.text = "$totalCount Item | ₹$totalPrice"
    }

    var presenter: HomePresenter? = null
    var foodItemAdapter: FoodItemAdapter? = null
    var context: Activity? = null
    private var behavior: BottomSheetBehavior<*>? = null
    private var dialog : Dialog? = null

    override fun getContentView(): Int {
        return R.layout.activity_home
    }

    override fun onCreatePresenter(): HomePresenter? {
        return HomePresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSupportActionBar(toolbar)
        context = this
        presenter = getPresenter()
        foodItemAdapter = FoodItemAdapter(this)
        text_view_cart.setOnClickListener(clickListener)
        behavior = BottomSheetBehavior.from(bottomsheet)

        recyclerView_foods.layoutManager = LinearLayoutManager(this)
        recyclerView_foods.adapter = foodItemAdapter

        foodItemAdapter?.setItemClickListener(object : OnListItemClickListener<FoodItems> {
            override fun onItemClick(view: View?, model: FoodItems, position: Int): Boolean {

                when (view?.id) {

                    R.id.image_food -> {
                        val pair1: Pair<View?, String?> = UtilPair.create<View, String>(view,
                                getString(R.string.picture_transition_name))
                        val options = ActivityOptions.makeSceneTransitionAnimation(context, pair1)
                        val movieIntent = Intent(applicationContext, ItemDetailsActivity::class.java)
                        movieIntent.putExtra(Constants.KEY_ITEM, model)
                        startActivity(movieIntent, options!!.toBundle())
                    }
                    R.id.image_add -> {
                        val count: Int = presenter?.getItemCount(model.item_name)!!
                        presenter?.addToCart(model, count.plus(1))
                        behavior?.state = BottomSheetBehavior.STATE_COLLAPSED;
                        foodItemAdapter?.notifyDataSetChanged()
                    }

                    R.id.image_remove -> {
                        presenter?.handleRemoveItem(model)
                        presenter?.updateBottomCart()
                        foodItemAdapter?.notifyDataSetChanged()
                    }

                }
                return false
            }
        })
        presenter?.getAllFoodItems()
    }

    override fun onResume() {
        super.onResume()
        presenter?.updateBottomCart()
        foodItemAdapter?.notifyDataSetChanged()
    }

    override fun onErrorMessage(resource: Int) {
        showToast(getString(resource))
    }

    override fun onEmptyItems(message: String) {
        showToast(message)
    }

    override fun updateFoodItems(foodItems: List<FoodItems>) {
        foodItemAdapter?.appendItems(foodItems)
    }

    private val clickListener = OnClickListener {
        when (it) {
            text_view_cart -> {
                val cartIntent = Intent(this, CartActivity::class.java)
                startActivity(cartIntent)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == R.id.action_filter) {
            showFilterDialog()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun showFilterDialog() {
        val alertLayout = layoutInflater.inflate(R.layout.filter_dialog_layout, null)
        val filter1 = alertLayout.findViewById<TextView>(R.id.text_filter_1)
        val filter2 = alertLayout.findViewById<TextView>(R.id.text_filter_2)

        filter1.setOnClickListener { it ->
            val allItems: List<FoodItems> = foodItemAdapter!!.allItems
            val sortedItems = allItems.sortedWith(CompareObjects.compareBy({ it.item_price }))
            foodItemAdapter?.clearItems()
            foodItemAdapter?.appendItems(sortedItems)
            dialog?.hide()
        }
        filter2.setOnClickListener { it ->
            val allItems = foodItemAdapter?.allItems
            val sortedItems = allItems?.sortedWith(CompareObjects.compareBy({ it.average_rating
            }))!!.asReversed()
            foodItemAdapter?.clearItems()
            foodItemAdapter?.appendItems(sortedItems)
            dialog?.hide()
        }

        val alert = AlertDialog.Builder(this)
        alert.setTitle(getString(R.string.filter))
        alert.setView(alertLayout)
        alert.setCancelable(false)
        alert.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            dialog?.dismiss()
        }

        dialog = alert.create()
        dialog?.show()

    }

}
