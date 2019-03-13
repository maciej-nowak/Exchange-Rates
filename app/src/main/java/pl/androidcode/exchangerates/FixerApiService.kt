package pl.androidcode.exchangerates

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FixerApiService {

    //do not place access key in that public way, this is only for this exercise purpose
    //always try to hide sensitive data
    @GET("{date}?access_key=19614b52d0cb361e902ccd8e929086e8")
    fun dayRates(@Path("date") date: String): Observable<ExchangeRateTable> //Call<ExchangeRateTable> for base usage

    companion object {
        fun create(): FixerApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create<FixerApiService>(FixerApiService::class.java)
        }
    }
}