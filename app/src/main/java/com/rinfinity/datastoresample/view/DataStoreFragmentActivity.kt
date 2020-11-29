package com.rinfinity.datastoresample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rinfinity.datastoresample.R

class DataStoreFragmentActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DataStoreFragmentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store_fragment)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MainFragment.newInstance())
            .addToBackStack(this.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount==0)
            finish()
    }
}