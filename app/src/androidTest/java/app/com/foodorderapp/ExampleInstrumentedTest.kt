package app.com.foodorderapp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import app.com.foodorderapp.data.callback.DaoResponse
import app.com.foodorderapp.data.dao.CartDao
import app.com.foodorderapp.data.model.FoodItems
import io.realm.Realm
import io.realm.RealmConfiguration

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private var realm: Realm? = null
    private var cartDao: CartDao? = null
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("app.com.foodorderapp", appContext.packageName)
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Realm.init(context)
        val config = RealmConfiguration.Builder().inMemory().name("test-realm").build()
        realm = Realm.getInstance(config)
        cartDao  = CartDao()
    }

}
