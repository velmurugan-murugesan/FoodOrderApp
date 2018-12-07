package app.com.foodorderapp.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class FoodOrderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        val realmConfiguration = RealmConfiguration.Builder()
                .schemaVersion(0)
                .migration { realm, oldVersion, newVersion ->
                    // DynamicRealm exposes an editable schema
                    val schema = realm.schema

                    // No major migration during development phase.
                    if (oldVersion == 0L) {

                    }
                }
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfiguration)

    }

}