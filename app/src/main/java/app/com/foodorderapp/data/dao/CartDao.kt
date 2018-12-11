package app.com.foodorderapp.data.dao

import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.model.realm.CartItem
import io.realm.Realm
import io.realm.RealmModel

class CartDao {

    fun <T : RealmModel?> storeOrUpdateCart(cartItem: T,
                                   callback: DaoResponse<T>) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync({ realm ->
            // As primary key is involved, use copy to realm or update as it will create or
            //  update based on object availability.
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

    fun <T : RealmModel?> getByKey(clazz: Class<T>, key: String, value: String,callback:
    DaoResponse<T>) : T? {
        var realm: Realm? = null
        var listResult: T? = null
        var data:T? = null
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm ->
                val realmResults = realm.where(clazz).
                        equalTo(key,value).findAll()
                listResult = realm.copyFromRealm(realmResults).first()
                callback.onSuccess(listResult)
            }

        }
        catch (e : Exception){
            callback.onFailure(e.message)
        }
        finally {
            realm?.close()
        }

        return listResult
    }

    fun getItemCount(value: String) : Int {
        var realm: Realm? = null
        var itemCount: Int = 0
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm ->
                val realmResults = realm.where(CartItem::class.java).equalTo("itemName",value)
                        .findFirst()
                realmResults.let {
                    itemCount = realm.copyFromRealm(it!!).itemCount
                }

                //listResult = realm.copyFromRealm(realmResults).itemCount

            }

        }
        catch (e : Exception){
            println(e.message)
            //callback.onFailure(e.message)
        }
        finally {
            realm?.close()
        }

        return itemCount
    }


    fun <T : RealmModel?> deleteByKey(clazz: Class<T>, key: String,value: String) : Boolean {
        var realm: Realm? = null
        var status = false
        try {
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm ->
                val realmResults = realm.where(clazz).equalTo(key,value).findAll()
                realmResults.deleteAllFromRealm()
                status = true
            }

        }catch (e : Exception){
            println("Exception = ${e.message}")
        }finally {
            realm?.close()
        }
        return status
    }


}