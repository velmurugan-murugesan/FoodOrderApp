package app.com.foodorderapp.helper

import app.com.foodorderapp.data.model.FoodItems

object CompareObjects {

    fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): java.util.Comparator<T> {
        return Comparator<T> { a, b -> compareValuesBy(a, b, *selectors) }
    }
}