package pl.androidcode.exchangerates.adapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExchangeItem(val date: String, val exchangeRate: ExchangeRate?) : Parcelable