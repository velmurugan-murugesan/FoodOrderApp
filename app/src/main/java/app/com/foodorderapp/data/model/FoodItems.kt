package app.com.foodorderapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class FoodItems(val average_rating: Float,
                     val image_url: String,
                     val item_name: String,
                     val item_price:Float) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(average_rating)
        parcel.writeString(image_url)
        parcel.writeString(item_name)
        parcel.writeFloat(item_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodItems> {
        override fun createFromParcel(parcel: Parcel): FoodItems {
            return FoodItems(parcel)
        }

        override fun newArray(size: Int): Array<FoodItems?> {
            return arrayOfNulls(size)
        }
    }
}