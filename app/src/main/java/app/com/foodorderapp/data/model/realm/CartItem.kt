package app.com.foodorderapp.data.model.realm

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CartItem(@PrimaryKey var itemName: String,
                    var itemRating: Float,
                    var itemImage: String,
                    var itemPrice: Float,
                    var itemCount: Int)  : RealmObject() {
    constructor() : this("",0.0f,"",0.0f,0)
}

