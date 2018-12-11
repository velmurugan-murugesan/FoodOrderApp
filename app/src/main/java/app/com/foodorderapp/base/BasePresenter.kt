package app.com.foodorderapp.base

import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import app.com.foodorderapp.data.model.realm.CartItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

open class BasePresenter<out V : BaseView>(view: V)  {

    private val mViewReference: WeakReference<V> = WeakReference(view)

    private val mCompositeDisposable = CompositeDisposable()

    open fun initialize() {
        //some initialization code
    }

    fun getView(): V? {
        return mViewReference.get()
    }

    fun <O> subscribe(single: Single<O>, observer: DisposableSingleObserver<O>) {
        mCompositeDisposable.add(single.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }


    fun cleanup() {
        mCompositeDisposable.clear()
    }

    open fun destroy() {
        cleanup()
        mViewReference.clear()
    }

    fun addToCart(foodItem: FoodItems?, count: Int) {

        foodItem?.let { it ->
            val cartItem = CartItem()
            cartItem.itemName = it.item_name
            cartItem.itemImage = it.image_url
            cartItem.itemPrice = it.item_price
            cartItem.itemRating = it.average_rating
            cartItem.itemCount = count

            CartDao().storeOrUpdateCart(cartItem,object : DaoResponse<CartItem> {
                override fun onSuccess(message: CartItem?) {
                    getView()?.onErrorMessage("Added to cart")
                }
                override fun onFailure(errorMessage: String?) {
                    getView()?.onErrorMessage(errorMessage!!)
                }
            })
        }
    }

    fun getItemCount(itemName: String) : Int {
        return CartDao().getItemCount(itemName)
    }

    fun deleteCart(itemName: String) {
        CartDao().deleteByKey(CartItem::class.java,"itemName", itemName)
    }

    fun getCartList(): List<CartItem> {
        return CartDao().getCartItems(CartItem::class.java)
    }

}