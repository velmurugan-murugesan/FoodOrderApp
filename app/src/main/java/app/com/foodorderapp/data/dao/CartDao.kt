package app.com.foodorderapp.data.dao

import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.model.realm.CartItem
import io.realm.Realm

class CartDao {

    fun storeOrUpdateCart(cartItem: CartItem,
                                   callback: DaoResponse) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ _ ->
            // As primary key is involved, use copy to realm or update as it will create or
            //  update based on object availability.
            realm.copyToRealmOrUpdate(cartItem)
        }, {
            callback.onSuccess("")
            realm.close()
        }, {
            callback.onFailure("")
            realm.close()
        })
    }

    fun getCartItems(): List<CartItem> {
        val realm = Realm.getDefaultInstance()
        val athleteRoutesList =
                realm.copyFromRealm(realm.where(CartItem::class.java).findAll())
        realm.close()
        return athleteRoutesList
    }





}