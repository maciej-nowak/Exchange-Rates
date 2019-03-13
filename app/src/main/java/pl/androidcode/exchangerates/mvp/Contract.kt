package pl.androidcode.exchangerates.mvp

import pl.androidcode.exchangerates.api.ExchangeRateTable

interface Contract {

    interface View {
        fun showProgress(enable: Boolean)
        fun showProgressLoadMore(enable: Boolean)
        fun showError()
        fun showErrorLoadMore()
        fun updateList(result: ExchangeRateTable)
    }

    interface Presenter {
        fun initialize(timestamp: Long = System.currentTimeMillis())
        fun uninitialize()
        fun loadRates()
        fun getCurrentDate(): Long
    }
}