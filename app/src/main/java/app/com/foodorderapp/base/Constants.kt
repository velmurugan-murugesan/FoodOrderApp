package app.com.foodorderapp.base

import android.content.Context
import android.util.TypedValue

object Constants {

    const val SYMBOL_RS = "₹"
    const val KEY_ITEMNAME = "itemName"
    const val KEY_ITEM = "item"
    const val COUPON_1 = "F22LABS"
    const val COUPON_2 = "FREEDEL"



    fun dpToPx(context: Context, value: Float): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics))
    }

    fun rupeesFormatter(amount: Any) : String {
        return Constants.SYMBOL_RS + amount
    }

    fun discountFormatter(amount: Any) : String {
        return "- " + Constants.SYMBOL_RS + amount
    }

    fun countFormatter(count: Int) : String {
        return count.toString()+"x"
    }

    fun rattingFormatter(rating: Float) : String {
        return String.format("%.1f",rating)
    }

}