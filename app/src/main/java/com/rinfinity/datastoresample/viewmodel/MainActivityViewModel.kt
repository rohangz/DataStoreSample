package com.rinfinity.datastoresample.viewmodel

import android.app.Application
import android.util.Log
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.*
import com.rinfinity.datastoresample.application.DataStoreSampleApplication
import com.rinfinity.datastoresample.view.EVEN_COUNTER
import com.rinfinity.datastoresample.view.ODD_COUNTER
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    companion object {
        const val TAG = "MainActivityViewModel"
    }

    val oddCounterPrefKey = preferencesKey<Int>(ODD_COUNTER)
    val evenCounterPrefKey = preferencesKey<Int>(EVEN_COUNTER)
    private val oddCounterStrKey = preferencesKey<String>(ODD_COUNTER)
    private val evenCounterStrKey = preferencesKey<String>(EVEN_COUNTER)

    private val mOddCounter = MutableLiveData(0)
    val oddCounter: LiveData<Int>
        get() = mOddCounter

    private val mEvenCounter = MutableLiveData(0)
    val evenCounter: LiveData<Int>
        get() = mEvenCounter


    private val oddCounterFlow = DataStoreSampleApplication.instance.dataStore.data.map { pref ->
        pref[oddCounterPrefKey] ?: 0
    }
    private val evenCounterFlow = DataStoreSampleApplication.instance.dataStore.data.map { pref ->
        pref[evenCounterPrefKey] ?: 0
    }

//    private val oddCounterStrFlow = DataStoreSampleApplication.instance.dataStore.data.map { pref ->
//        pref[oddCounterStrKey]
//    }
//
//    private val evenCounterStrFlow =
//        DataStoreSampleApplication.instance.dataStore.data.map { pref ->
//            pref[evenCounterStrKey]
//        }

    init {
        viewModelScope.launch {

            async {
                oddCounterFlow.collect {
                    Log.d(TAG, "oddCounterFlow Collected")
                    mOddCounter.value = it
                }
            }

            async {
                evenCounterFlow.collect {
                    Log.d(TAG, "evenCounterFlow Collected")
                    mEvenCounter.value = it
                }
            }

        }

//        viewModelScope.launch {
//            oddCounterStrFlow
//                .catch {
//                    emit(null)
//                }
//                .collect {
//                    Log.d(TAG, it ?: "nullOdd")
//                }
//        }
//
//        viewModelScope.launch {
//            evenCounterStrFlow
//                .catch {
//                    emit(null)
//                }
//                .collect {
//                    Log.d(TAG, it ?: "nullEven")
//                }
//        }
    }


}