package com.rinfinity.datastoresample.viewmodel

import android.app.Application
import android.util.Log
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rinfinity.datastoresample.application.DataStoreSampleApplication
import com.rinfinity.datastoresample.model.UserModel
import com.rinfinity.datastoresample.view.EVEN_COUNTER
import com.rinfinity.datastoresample.view.ODD_COUNTER
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DataStoreProtoViewModel(app: Application) : AndroidViewModel(app) {
    companion object {
        const val TAG = "DataStoreProtoViewModel"
    }

    val oddCounterPrefKey = preferencesKey<Int>(ODD_COUNTER)
    val evenCounterPrefKey = preferencesKey<Int>(EVEN_COUNTER)

    private val mUserModel = MutableLiveData(UserModel.getDefaultInstance())
    val userModel: LiveData<UserModel>
        get() = mUserModel

    init {
        viewModelScope.launch {
            async {
                DataStoreSampleApplication.instance.userModelStore.data.collect {
                    Log.d(
                        TAG,
                        "DataStoreSampleApplication.instance.userModelStore.data got collected"
                    )
                    mUserModel.value = it
                }
            }

        }
    }


    fun onFirstNameChange(firstName: String) {
        viewModelScope.launch {
            DataStoreSampleApplication.instance.userModelStore.updateData { data ->
                data.toBuilder().setFirstName(firstName).build()
            }
        }
    }

    fun onLastNameChanged(lastName: String) {
        viewModelScope.launch {
            DataStoreSampleApplication.instance.userModelStore.updateData { data ->
                data.toBuilder().setLastName(lastName).build()
            }
        }
    }

    fun onDeptNameChanged(deptName: String) {
        viewModelScope.launch {
            DataStoreSampleApplication.instance.userModelStore.updateData { data ->
                data.toBuilder().setDeptName(deptName).build()
            }
        }
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        viewModelScope.launch {
            DataStoreSampleApplication.instance.userModelStore.updateData { data ->
                data.toBuilder().setPhoneNumber(phoneNumber).build()
            }
        }
    }
}