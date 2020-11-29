package com.rinfinity.datastoresample.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rinfinity.datastoresample.R
import com.rinfinity.datastoresample.application.DataStoreSampleApplication
import com.rinfinity.datastoresample.model.UserModel
import com.rinfinity.datastoresample.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

const val ODD_COUNTER = "oddCounter"
const val EVEN_COUNTER = "evenCounter"

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainActivityViewModel::class.java
            )
        setOnClickListeners()
        initObservers()
    }


    private fun setOnClickListeners() {
        btn_1.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                DataStoreSampleApplication.instance.dataStore.edit { pref ->
                    val counter = pref[mViewModel.oddCounterPrefKey] ?: 0
                    pref[mViewModel.oddCounterPrefKey] = counter + 1
                }
            }
        }
        btn_2.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                DataStoreSampleApplication.instance.dataStore.edit { pref ->
                    val counter = pref[mViewModel.evenCounterPrefKey] ?: 0
                    pref[mViewModel.evenCounterPrefKey] = counter + 1
                }
            }
        }
        btn_3.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                DataStoreSampleApplication.instance.dataStore.edit { pref ->
                    val counter = pref[mViewModel.oddCounterPrefKey] ?: 0
                    pref[mViewModel.oddCounterPrefKey] = counter + 1
                }
            }
        }
        btn_4.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                DataStoreSampleApplication.instance.dataStore.edit { pref ->
                    val counter = pref[mViewModel.evenCounterPrefKey] ?: 0
                    pref[mViewModel.evenCounterPrefKey] = counter + 1
                }
            }
        }

        btn_5.setOnClickListener {
            Intent(this, DataStoreProtoActivity::class.java).apply {
                startActivity(this)
            }
        }

        btn_6.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                DataStoreSampleApplication.instance.userModelStore.updateData {
                    it.toBuilder()
                        .setFirstName("Dummy")
                        .setLastName("Dummy")
                        .setDeptName("Dummy")
                        .setPhoneNumber("9912394")
                        .build()
                }
            }
        }
    }

    private fun initObservers() {
        mViewModel.oddCounter.observe(this, Observer {
            txt_odd_counter.text = it.toString()
        })

        mViewModel.evenCounter.observe(this, Observer {
            txt_even_counter.text = it.toString()
        })
    }
}