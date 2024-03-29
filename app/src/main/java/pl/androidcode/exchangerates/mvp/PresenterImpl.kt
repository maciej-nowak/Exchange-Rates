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

    override fun loadRates() {
        showProgress(true)
        interactor.fetchData(getDateFormat())
    }

    override fun onFetchDataSuccess(results: ExchangeRateTable) {
        view.updateList(results)
        showProgress(false)
        downgradeDateByOneDay()
    }

    override fun onFetchDataFailed() {
        if(isToday()) {
            view.showError()
        } else {
            view.showErrorLoadMore()
        }
        showProgress(false)
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

    private fun showProgress(enable: Boolean) {
        if(isToday()) {
            view.showProgress(enable)
        } else {
            view.showProgressLoadMore(enable)
        }
    }

    private fun isToday(): Boolean {
        val calendarToday = Calendar.getInstance()
        return (calendar.get(Calendar.ERA) == calendarToday.get(Calendar.ERA) &&
                calendar.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == calendarToday.get(Calendar.DAY_OF_YEAR))
    }
}