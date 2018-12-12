package app.com.foodorderapp.feature.cart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.com.foodorderapp.R
import app.com.foodorderapp.base.BaseListAdapter
import app.com.foodorderapp.data.model.realm.CartItem
import app.com.foodorderapp.helper.FormatHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class CartAdapter(context: Context) : BaseListAdapter<RecyclerView.ViewHolder, CartItem>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CartViewHolder(layoutInflater.inflate(R.layout.cart_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item : CartItem? = getItem(position)
        item?.let {
            val historyViewHolder = holder as CartAdapter.CartViewHolder
            historyViewHolder.bind(it)
        }    }

    inner class CartViewHolder(itemView: View) : BaseViewHolder<CartItem>(itemView) {
        private var itemName: TextView = itemView.findViewById(R.id.text_cart_title)
        private var itemImage: ImageView = itemView.findViewById(R.id.image_cart_item)
        private var itemTotal: TextView = itemView.findViewById(R.id.text_cart_subtotal)

        private val itemCount: TextView = itemView.findViewById(R.id.text_cart_item_count)


        override fun bind(item: CartItem?) {
            super.bind(item)
            item?.let {
                itemName.text = it.itemName
                val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(5))

                Glide.with(context)
                        .load(it.itemImage)
                        .apply(requestOptions)
                        .into(itemImage)
                itemCount.text = context.getString(R.string.count_formatter,it.itemCount)
                itemTotal.text = context.getString(R.string.rupees_formatter,FormatHelper.priceDecimalFormatter(it.itemCount * it.itemPrice))
            }
        }
    }
}