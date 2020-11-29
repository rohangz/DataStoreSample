package com.rinfinity.datastoresample.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.rinfinity.datastoresample.model.UserModel
import com.rinfinity.datastoresample.serializer.UserModelSerializer

private lateinit var mInstance: DataStoreSampleApplication

class DataStoreSampleApplication : Application() {

    companion object {
        val instance: DataStoreSampleApplication
            get() = mInstance
    }

    private val mApplicationPrefDataStoreName = "ApplicationPrefDataStore"
    private val mUserModelFileName = "userModelData.pb"
    val dataStore: DataStore<Preferences> = createDataStore(name = mApplicationPrefDataStoreName)
    val userModelStore: DataStore<UserModel> = createDataStore(
        fileName = mUserModelFileName,
        serializer = UserModelSerializer
    )

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

}