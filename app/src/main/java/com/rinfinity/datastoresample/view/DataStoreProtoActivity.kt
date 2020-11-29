package com.rinfinity.datastoresample.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rinfinity.datastoresample.R
import com.rinfinity.datastoresample.application.DataStoreSampleApplication
import com.rinfinity.datastoresample.viewmodel.DataStoreProtoViewModel
import kotlinx.android.synthetic.main.activity_data_store_proto.*
import kotlinx.android.synthetic.main.activity_data_store_proto.btn_1
import kotlinx.android.synthetic.main.activity_data_store_proto.btn_2
import kotlinx.android.synthetic.main.activity_data_store_proto.btn_3
import kotlinx.android.synthetic.main.activity_main.*

class DataStoreProtoActivity : AppCompatActivity() {

    private lateinit var mViewModel: DataStoreProtoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store_proto)
        mViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                DataStoreProtoViewModel::class.java
            )
        setOnClickListeners()
        setOnTextChangeListeners()
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
            Intent(this, DataStoreFragmentActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setOnTextChangeListeners() {
        input_first_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                mViewModel.onFirstNameChange(p0?.toString() ?: "")
        })
        input_last_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                mViewModel.onLastNameChanged(p0?.toString() ?: "")
        })
        input_dept_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                mViewModel.onDeptNameChanged(p0?.toString() ?: "")
        })
        input_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                mViewModel.onPhoneNumberChanged(p0?.toString() ?: "")
        })
    }

    private fun initObservers() {
        mViewModel.userModel.observe(this, Observer { userModel ->
            value_first_name.text = userModel.firstName
            value_last_name.text = userModel.lastName
            value_dept_name.text = userModel.deptName
            value_phone_number.text = userModel.phoneNumber
            if (userModel.firstName != input_first_name.text.toString())
                input_first_name.setText(userModel.firstName)
            if (userModel.lastName != input_last_name.text.toString())
                input_last_name.setText(userModel.lastName)
            if (userModel.deptName != input_dept_name.text.toString())
                input_dept_name.setText(userModel.deptName)
            if (userModel.phoneNumber != input_phone_number.text.toString())
                input_phone_number.setText(userModel.phoneNumber)

        })
    }
}