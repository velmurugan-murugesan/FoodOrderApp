package app.com.foodorderapp.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.com.foodorderapp.helper.KeyboardUtils

abstract class BaseActivity<out P : BasePresenter<*>> : AppCompatActivity() {

    private var mPresenter: P? = null
    protected var container: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        mPresenter = onCreatePresenter()
        onActivityCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onActivityCreated(savedInstanceState: Bundle?) {

    }

    protected open fun onCreatePresenter(): P? = null

    protected open fun getPresenter(): P? = mPresenter

    protected open fun hideKeyboard(view: View) {
        KeyboardUtils.hideKeyboard(applicationContext, view)
    }

    override fun onDestroy() {
        mPresenter?.destroy()
        super.onDestroy()
    }

    protected fun showToast(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    abstract fun getContentView(): Int
}