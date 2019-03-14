package pl.androidcode.exchangerates.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.androidcode.exchangerates.R
import pl.androidcode.exchangerates.adapter.model.ExchangeItem
import java.util.*

class ExchangeAdapter(val items: MutableList<ExchangeItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencyName: HashMap<String, String> = hashMapOf()
    init {
        for (item in items) {
            item.exchangeRate?.currency?.let {
                try {
                    currencyName.put(it, Currency.getInstance(it).displayName)
                } catch (exception: Exception) {
                    //currency code not supported
                }
            }
        }
    }

    companion object {
        const val EXCHANGE = 1
        const val DATE = 2
    }

    interface OnItemClickListener {
        fun onItemClick(item: ExchangeItem)
    }

    class ExchangeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val codeView: TextView
        val nameView: TextView
        val valueView: TextView
        init {
            codeView = view.findViewById(R.id.currency_code)
            nameView = view.findViewById(R.id.currency_name)
            valueView = view.findViewById(R.id.currency_value)
        }
    }

    class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateView: TextView
        init {
            dateView = view.findViewById(R.id.date_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == EXCHANGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exchange, parent, false) as View
            ExchangeViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false) as View
            DateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(holder.itemViewType == EXCHANGE) {
            holder as ExchangeViewHolder
            holder.codeView.text = item.exchangeRate?.currency
            holder.nameView.text = currencyName[item.exchangeRate?.currency]
            holder.valueView.text = "%.4f".format(item.exchangeRate?.rate)
            holder.view.setOnClickListener { listener.onItemClick(item) }
        } else {
            holder as DateViewHolder
            holder.dateView.text = item.date
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(items[position].exchangeRate != null)
            EXCHANGE
        else
            DATE
    }
}