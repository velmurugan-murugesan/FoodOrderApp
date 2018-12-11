package app.com.foodorderapp.data.dao

import android.util.Log
import app.com.foodorderapp.base.Constants
import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.model.realm.CartItem
import io.realm.Realm
import io.realm.RealmModel

class CartDao {

    fun <T : RealmModel?> storeOrUpdateCart(cartItem: T,
                                            callback: DaoResponse<T>) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->
            realm.copyToRealmOrUpdate(cartItem)
        }, {
            callback.onSuccess(cartItem)
            realm.close()
        }, {
            callback.onFailure(it.message)
            realm.close()
        })

    }

    fun <T : RealmModel> getCartItems(clazz: Class<T>): List<T> {
        val realm = Realm.getDefaultInstance()
        val cartList =
                realm.copyFromRealm(realm.where(clazz).findAll())
        realm.close()
        return cartList
    }

    fun getItemCount(value: String): Int {
        var realm: Realm? = null
        var itemCount: Int = 0
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm ->
                val realmResults = realm.where(CartItem::class.java).equalTo(Constants.KEY_ITEMNAME, value)
                        .findFirst()
                realmResults.let {
                    itemCount = realm.copyFromRealm(it!!).itemCount
                }
            }
        } catch (e: Exception) {
            itemCount = 0
            Log.d("getItemCount", e.toString())
        } finally {
            realm?.close()
        }

        return itemCount
    }


    fun <T : RealmModel?> deleteByKey(clazz: Class<T>, key: String, value: String): Boolean {
        var realm: Realm? = null
        var status = false
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm ->
                val realmResults = realm.where(clazz).equalTo(key, value).findAll()
                realmResults.deleteAllFromRealm()
                status = true
            }

        } catch (e: Exception) {
            Log.d("deleteByKey", e.toString())
        } finally {
            realm?.close()
        }
        return status
    }

}