package pl.androidcode.exchangerates.api

data class ExchangeRateTable(val base: String, val date: String, val rates: Map<String, Double>)
