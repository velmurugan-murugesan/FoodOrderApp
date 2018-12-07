package app.com.foodorderapp.feature.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.View
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseActivity
import app.com.foodorderapp.feature.cart.CartActivity
import app.com.foodorderapp.data.callback.OnListItemClickListener
import app.com.foodorderapp.data.model.FoodItems
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.food_item_layout.*
import android.util.Pair as UtilPair

class HomeActivity : BaseActivity<HomePresenter>(), HomeView {

    var presenter: HomePresenter? = null
    var foodItemAdapter: FoodItemAdapter? = null
    var context: Activity? = null

    override fun getContentView(): Int {
        return R.layout.activity_home
    }

    override fun onCreatePresenter(): HomePresenter? {
        return HomePresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context = this as Activity
        presenter = getPresenter()
        foodItemAdapter = FoodItemAdapter(this)

        recyclerview_foods.layoutManager = LinearLayoutManager(this)
        recyclerview_foods.adapter = foodItemAdapter

        foodItemAdapter?.setItemClickListener(object : OnListItemClickListener<FoodItems> {
            override fun onItemClick(view: View?, model: FoodItems?, position: Int): Boolean {

                when(view?.id){

                    R.id.image_food -> {
                        val pair1: Pair<View?,String?> = UtilPair.create<View,String>(view,getString(R
                                .string
                                .picture_transition_name))
                        val pair2: Pair<View?,String?> = UtilPair.create<View,String>(text_food_name,
                                getString(R.string
                                        .title_transition_name))

                        val options = ActivityOptions.makeSceneTransitionAnimation(context,pair1,pair2)
                        /*options = ActivityOptions.makeSceneTransitionAnimation(context as HomeActivity,
                                transitionPairs)*/
                        val movieIntent = Intent(applicationContext, CartActivity::class.java)
                        movieIntent.putExtra("item", model)
                        startActivity(movieIntent, options!!.toBundle())
                    }
                    R.id.image_add -> {
                        showToast("added")
                    }

                    R.id.image_remove -> {
                        showToast("Removed")
                    }

                }

                return false
            }
        })

        presenter?.getAllFoodItems()
    }

    override fun onErrorMessage(message: String) {
        showToast(message)
    }

    override fun onEmptyItems(message: String) {
        showToast(message)
    }

    override fun updateFoodItems(foodItems: List<FoodItems>) {
        foodItemAdapter?.appendItems(foodItems)
    }
}
