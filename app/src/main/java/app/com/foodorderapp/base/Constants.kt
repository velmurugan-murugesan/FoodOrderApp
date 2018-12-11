package app.com.foodorderapp.base

import android.content.Context
import android.util.TypedValue

object Constants {

    const val SYMBOL_RS = "₹"
    const val COUPON_1 = "F22LABS"
    const val COUPON_2 = "FREEDEL"


    fun dpToPx(context: Context, value: Float): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics))
    }


}