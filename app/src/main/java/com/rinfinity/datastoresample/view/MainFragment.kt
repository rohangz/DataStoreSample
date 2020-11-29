package com.rinfinity.datastoresample.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.rinfinity.datastoresample.R
import com.rinfinity.datastoresample.application.DataStoreSampleApplication
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment(R.layout.fragment_main) {
    private val mOddKey = preferencesKey<Int>(ODD_COUNTER)
    private val mEvenKey = preferencesKey<Int>(EVEN_COUNTER)

    companion object {
        const val TAG = "MainFragment"

        @JvmStatic
        fun newInstance(bundle: Bundle? = null) =
            MainFragment().apply {
                if (bundle != null) arguments = bundle
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        lifecycleScope.launchWhenStarted {
            DataStoreSampleApplication.instance.dataStore.data.collect { pref ->
                val a = pref[mOddKey] ?: 0
                val b = pref[mEvenKey] ?: 0
                Log.d(TAG, "odd $a")
                Log.d(TAG, "even $b")
            }
        }
        lifecycleScope.launchWhenStarted {
            DataStoreSampleApplication.instance.userModelStore.data.collect {
                Log.d(TAG, "UserModel $it")
            }
        }
    }


    private fun setOnClickListeners() {
        frag_main_btn_1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, DemoDataStoreFragment.newInstance())
                .addToBackStack(this.javaClass.simpleName)
                .commit()
        }
    }
}