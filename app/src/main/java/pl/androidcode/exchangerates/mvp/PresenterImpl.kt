package pl.androidcode.exchangerates.mvp

import pl.androidcode.exchangerates.api.ExchangeRateTable
import java.text.SimpleDateFormat
import java.util.*

class PresenterImpl(private val view: Contract.View) : Contract.Presenter, Interactor.OnResult {

    private val interactor: Interactor = InteractorImpl(this)

    private lateinit var calendar: Calendar
    private lateinit var currentDate: Date

    override fun initialize(timestamp: Long) {
        calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        currentDate = calendar.time
        interactor.initialize()
    }

    override fun uninitialize() {
        interactor.uninitialize()
    }

    override fun load() {
        view.showProgress(true)
        interactor.fetchData(getDateFormat())
    }

    override fun onFetchDataSuccess(results: ExchangeRateTable) {
        view.updateList(results)
        view.showProgress(false)
        downgradeDateByOneDay()
    }

    override fun onFetchDataFailed() {
        view.showError()
        view.showProgress(false)
    }

    override fun getCurrentDate(): Long {
        return currentDate.time
    }

    private fun getDateFormat(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(currentDate)
    }

    private fun downgradeDateByOneDay() {
        calendar.add(Calendar.DATE, -1)
        currentDate = calendar.time
    }
}