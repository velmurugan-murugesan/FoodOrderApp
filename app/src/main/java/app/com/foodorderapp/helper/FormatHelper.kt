package app.com.foodorderapp.helper

object FormatHelper {

    fun rattingDecimalFormatter(rating: Float) : String {
        return String.format("%.1f",rating)
    }

    fun priceDecimalFormatter(rating: Float) : String {
        return String.format("%.2f",rating)
    }
}