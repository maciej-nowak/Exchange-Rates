package pl.androidcode.exchangerates.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.androidcode.exchangerates.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXCHANGE_ITEM = "EXCHANGE_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}
