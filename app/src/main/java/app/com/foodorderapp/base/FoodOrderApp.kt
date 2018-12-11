package app.com.foodorderapp.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class FoodOrderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration { realm, oldVersion, newVersion ->
                    val schema = realm.schema
                    if (oldVersion == 0L) {
                    }
                }
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

}