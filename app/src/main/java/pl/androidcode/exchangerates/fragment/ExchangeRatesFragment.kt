package pl.androidcode.exchangerates.fragment

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.androidcode.exchangerates.R
import pl.androidcode.exchangerates.activity.DetailsActivity
import pl.androidcode.exchangerates.activity.DetailsActivity.Companion.EXCHANGE_ITEM
import pl.androidcode.exchangerates.adapter.ExchangeAdapter
import pl.androidcode.exchangerates.adapter.model.ExchangeItem

class ExchangeRatesFragment : Fragment(), ExchangeAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exchangeAdapter: ExchangeAdapter
    private lateinit var items: ArrayList<ExchangeItem>
    private var callback: OnLoadMoreCallback? = null

    companion object {
        const val TAG = "ExchangeRatesFragment"
        const val EXCHANGE_RATES = "EXCHANGE_RATES"
        const val ITEMS_THRESHOLD = 30

        fun newInstance(list: List<ExchangeItem>): ExchangeRatesFragment {
            val args = Bundle()
            args.putParcelableArrayList(EXCHANGE_RATES, ArrayList(list))
            val fragment = ExchangeRatesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnLoadMoreCallback {
        fun loadMore()
    }

    fun setOnLoadMoreCallback(callback: OnLoadMoreCallback) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if(it.containsKey(EXCHANGE_RATES))
                items = it.getParcelableArrayList(EXCHANGE_RATES)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exchange_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeAdapter = ExchangeAdapter(items, this)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = exchangeAdapter
        }
        initScrollListener()
    }

    override fun onItemClick(item: ExchangeItem) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(EXCHANGE_ITEM, item)
        startActivity(intent)
    }

    fun update(newItems: List<ExchangeItem>) {
        items.addAll(newItems)
        exchangeAdapter.notifyDataSetChanged()
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isListBottom()) {
                    callback?.loadMore()
                }
            }
        })
    }

    private fun isListBottom(): Boolean {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
        return linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == exchangeAdapter.items.size - 1 - ITEMS_THRESHOLD
    }
}