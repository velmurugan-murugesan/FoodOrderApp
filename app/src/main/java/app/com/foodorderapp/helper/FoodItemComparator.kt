package app.com.foodorderapp.helper

import app.com.foodorderapp.data.model.FoodItems

class FoodItemComparator {
    class StatusComparator : Comparator<FoodItems> {

        override fun compare(item1: FoodItems, item2: FoodItems): Int {
            return (item1.item_price - item2.item_price).toInt()
        }
    }
}