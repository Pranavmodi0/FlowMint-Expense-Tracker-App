package com.only.flowmint

import android.app.Application
import com.only.flowmint.data.Authentication
import com.only.flowmint.data.Category
import com.only.flowmint.models.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object {
        const val maxdb = 100 * 1024 * 1024
        lateinit var db: Realm
    }

    override fun onCreate() {
        super.onCreate()
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Expense::class,
                Category::class,
                Authentication::class
            )
        )
            .compactOnLaunch { totalBytes, usedBytes ->
                (totalBytes > maxdb) && ((usedBytes / totalBytes) < 0.5)
            }
            .build()
        db = Realm.open(config)
    }
}