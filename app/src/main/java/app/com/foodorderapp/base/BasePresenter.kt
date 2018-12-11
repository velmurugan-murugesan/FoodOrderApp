package app.com.foodorderapp.base

import android.util.Log
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

open class BasePresenter<out V : BaseView>(view: V) {

    private val mViewReference: WeakReference<V> = WeakReference(view)

    private val mCompositeDisposable = CompositeDisposable()

    fun getView(): V? {
        return mViewReference.get()
    }

    fun <O> subscribe(single: Single<O>, observer: DisposableSingleObserver<O>) {
        mCompositeDisposable.add(single.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }


    private fun cleanup() {
        mCompositeDisposable.clear()
    }

    open fun destroy() {
        cleanup()
        mViewReference.clear()
    }



}