package app.com.foodorderapp.base

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
}