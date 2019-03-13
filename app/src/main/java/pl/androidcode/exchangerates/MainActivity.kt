package pl.androidcode.exchangerates

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.androidcode.exchangerates.api.ExchangeRateTable
import pl.androidcode.exchangerates.mvp.Contract
import pl.androidcode.exchangerates.mvp.PresenterImpl

class MainActivity : AppCompatActivity(), Contract.View {

    private val presenter by lazy { PresenterImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.initialize()
        presenter.load()
    }

    override fun onDestroy() {
        presenter.uninitialize()
        super.onDestroy()
    }

    override fun showProgress(enable: Boolean) {
        //TODO hide or show progress bar
    }

    override fun showError() {
        //TODO show some error message
    }

    override fun updateList(result: ExchangeRateTable) {
        //TODO add new items to some list
    }
}
