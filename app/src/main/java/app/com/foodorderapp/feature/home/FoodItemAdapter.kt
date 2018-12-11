package app.com.foodorderapp.feature.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseListAdapter
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import com.bumptech.glide.Glide

class FoodItemAdapter(context: Context) : BaseListAdapter<RecyclerView.ViewHolder, FoodItems>
(context) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: FoodItems? = getItem(position)
        item?.let {
            val historyViewHolder = holder as FoodItemViewHolder
            historyViewHolder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FoodItemViewHolder {
        return FoodItemViewHolder(layoutInflater.inflate(R.layout.food_item_layout, parent, false))
    }

    inner class FoodItemViewHolder(itemView: View) : BaseViewHolder<FoodItems>(itemView) {
        private val foodImage: ImageView = itemView.findViewById(R.id.image_food)
        private val foodName: TextView = itemView.findViewById(R.id.text_food_name)
        private val foodPrice: TextView = itemView.findViewById(R.id.text_food_price)
        private val itemRating: TextView = itemView.findViewById(R.id.text_rating)
        private val itemCount: TextView = itemView.findViewById(R.id.text_item_count)
        private val removeItem: ImageView = itemView.findViewById(R.id.image_remove)
        private val addItem: ImageView = itemView.findViewById(R.id.image_add)

        init {
            setClickListener(foodImage, addItem, removeItem)
        }

        override fun bind(item: FoodItems?) {
            super.bind(item)
            item?.let {
                foodName.text = it.item_name
                Glide.with(context)
                        .load(it.image_url)
                        .into(foodImage)
                itemCount.text = CartDao().getItemCount(it.item_name).toString()
                foodPrice.text = Constants.rupeesFormatter(it.item_price)
                itemRating.text = context.getString(R.string.rating_formatter, Constants.rattingFormatter(it.average_rating))
            }
        }
    }

}