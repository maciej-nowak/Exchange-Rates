package pl.androidcode.exchangerates.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_date.*
import pl.androidcode.exchangerates.R
import pl.androidcode.exchangerates.adapter.model.ExchangeItem

class DetailsActivity : AppCompatActivity() {

    //simple Activity showing passed args, no need to use Fragment

    companion object {
        const val EXCHANGE_ITEM = "EXCHANGE_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initActionBar()
        intent.extras?.let {
            if(it.containsKey(EXCHANGE_ITEM)) {
                val item = it.get(EXCHANGE_ITEM) as ExchangeItem
                initView(item)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initView(item: ExchangeItem) {
        if(item.exchangeRate != null) {
            val currencyCode = item.exchangeRate.currency
            val rate = item.exchangeRate.rate
            val baseRate = 1 / rate
            currency_code_buy.text = currencyCode
            currency_code_sell.text = currencyCode
            currency_rate_buy.text = "%.4f".format(rate)
            currency_base_rate_buy.text = "%.4f".format(baseRate)
        }
        date_name.text = item.date
    }
}
