package pl.androidcode.exchangerates.mvp

import pl.androidcode.exchangerates.api.ExchangeRateTable

interface Interactor {

    fun initialize()
    fun uninitialize()
    fun fetchData(date: String)
    
    interface OnResult {
        fun onFetchDataSuccess(results: ExchangeRateTable)
        fun onFetchDataFailed()
    }
}