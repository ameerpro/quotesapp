package com.hamzabhatti.quotes.ui.quote

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hamzabhatti.quotes.R
import com.hamzabhatti.quotes.data.QuotesViewModel
import com.hamzabhatti.quotes.data.models.Quote
import com.hamzabhatti.quotes.databinding.ActivityQuotesListBinding
import com.hamzabhatti.quotes.utils.ApplicationEnum


class QuotesListActivity : AppCompatActivity() {

    private lateinit var quoteViewModel: QuotesViewModel
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var mBinding: ActivityQuotesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quotes_list)

        //Setting up RecyclerView
        mBinding.mainHolder.rvQuoteList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        quotesAdapter = QuotesAdapter()
        mBinding.mainHolder.rvQuoteList.adapter = quotesAdapter

        //Setting up ViewModel and LiveData
        quoteViewModel = ViewModelProviders.of(this).get(QuotesViewModel::class.java)

        quoteViewModel.getApiStatus().observe(this, Observer { apiStatus ->

            mBinding.swipeRefresh.isRefreshing = false

            if (apiStatus.applicationEnum == ApplicationEnum.QUOTE_GET_SUCCESSFUL) {


                val quotesItems: List<Quote> = Gson().fromJson(apiStatus.jsonObject().get("quotes"), object : TypeToken<List<Quote>>() {}.type)

                quotesAdapter.setAllQuotes(quotesItems)

            } else {

                Toast.makeText(this, apiStatus.message, Toast.LENGTH_LONG).show()
            }
        })

        quoteViewModel.getAllQuotes().observe(this, Observer {
            if (it.isEmpty()) {
                mBinding.swipeRefresh.isRefreshing = true
                quoteViewModel.getQuotesRemote()
            }
            else
                quotesAdapter.setAllQuotes(it)
        })

    }

}
