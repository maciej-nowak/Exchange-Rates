package pl.androidcode.exchangerates.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.androidcode.exchangerates.api.FixerApiService

class InteractorImpl(private val onResult: Interactor.OnResult) : Interactor {

    private val service by lazy { FixerApiService.create() }
    private var disposable: Disposable? = null
    private var isLoading = false

    override fun initialize() {
    }

    override fun uninitialize() {
        disposable?.dispose()
    }

    override fun fetchData(date: String) {
        if(!isLoading) {
            isLoading = true
            getExchangeRate(date)
        }
    }

    private fun getExchangeRate(date: String) {
        disposable = service.dayRates(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    isLoading = false
                    onResult.onFetchDataSuccess(result)
                },
                { error ->
                    isLoading = false
                    onResult.onFetchDataFailed()
                }
            )
    }
}