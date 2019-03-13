package pl.androidcode.exchangerates

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pl.androidcode.exchangerates.adapter.model.ExchangeItem
import pl.androidcode.exchangerates.adapter.model.ExchangeRate
import pl.androidcode.exchangerates.api.ExchangeRateTable
import pl.androidcode.exchangerates.mvp.Contract
import pl.androidcode.exchangerates.mvp.PresenterImpl

class MainActivity : AppCompatActivity(), Contract.View, ExchangeRatesFragment.OnLoadMoreCallback {

    companion object {
        const val CURRENT_DATE = "CURRENT_DATE"
    }

    private val presenter by lazy { PresenterImpl(this) }
    private var fragment: ExchangeRatesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null && savedInstanceState.containsKey(CURRENT_DATE)) {
            presenter.initialize(savedInstanceState.getLong(CURRENT_DATE))
        } else {
            presenter.initialize()
            presenter.loadRates()
        }
    }

    override fun onDestroy() {
        presenter.uninitialize()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(CURRENT_DATE, presenter.getCurrentDate())
    }

    override fun onAttachFragment(fragment: Fragment?) {
        if(fragment is ExchangeRatesFragment) {
            this.fragment = fragment
            fragment.setOnLoadMoreCallback(this)
        }
    }

    override fun showProgress(enable: Boolean) {
        if(enable) {
            progress_bar_container.visibility = View.VISIBLE
        } else {
            progress_bar_container.visibility = View.GONE
        }
    }

    override fun showProgressLoadMore(enable: Boolean) {
        if(enable) {
            progress_bar_bottom.visibility = View.VISIBLE
        } else {
            progress_bar_bottom.visibility = View.GONE
        }
    }

    override fun showError() {
        exchange_rates_container.visibility = View.GONE
        progress_bar_container.visibility = View.GONE
        error_container.visibility = View.VISIBLE
    }

    override fun showErrorLoadMore() {
        Toast.makeText(this, getString(R.string.load_previous_rates_failed), Toast.LENGTH_LONG).show()
    }

    override fun updateList(result: ExchangeRateTable) {
        val items: MutableList<ExchangeItem> = mutableListOf()
        items.add(ExchangeItem(result.date, null))
        for((key, value) in result.rates) {
            items.add(ExchangeItem(result.date, ExchangeRate(key, value)))
        }
        if(fragment == null) {
            createFragment(items)
        } else {
            fragment?.update(items)
        }
    }

    override fun loadMore() {
        presenter.loadRates()
    }

    private fun createFragment(items: List<ExchangeItem>) {
        fragment = ExchangeRatesFragment.newInstance(items)
        supportFragmentManager.beginTransaction()
            .replace(R.id.exchange_rates_container, fragment!!, ExchangeRatesFragment.TAG)
            .commit()
    }
}
