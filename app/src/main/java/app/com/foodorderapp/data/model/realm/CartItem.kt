package app.com.foodorderapp.data.model.realm

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CartItem(@PrimaryKey @SerializedName(value = "item_name") var itemName: String,
                    @SerializedName(value = "item_rating") var itemRating: Float,
                    @SerializedName(value = "item_image") var itemImage: String,
                    @SerializedName(value = "item_price") var itemPrice: Float,
                    @SerializedName(value = "item_count") var itemCount: Int)  : RealmObject() {
    constructor() : this("",0.0f,"",0.0f,0)
}

