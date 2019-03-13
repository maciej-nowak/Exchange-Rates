package pl.androidcode.exchangerates.mvp

import pl.androidcode.exchangerates.api.ExchangeRateTable

interface Contract {

    interface View {
        fun showProgress(enable: Boolean)
        fun showError()
        fun updateList(result: ExchangeRateTable)
    }

    interface Presenter {
        fun initialize(timestamp: Long = System.currentTimeMillis())
        fun uninitialize()
        fun load()
        fun getCurrentDate(): Long
    }
}