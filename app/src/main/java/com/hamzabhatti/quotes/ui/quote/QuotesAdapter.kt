package com.hamzabhatti.quotes.ui.quote

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamzabhatti.quotes.R
import com.hamzabhatti.quotes.data.models.Quote
import com.hamzabhatti.quotes.databinding.QuoteItemBinding
import kotlinx.android.synthetic.main.quote_item.view.*
import java.util.*


class QuotesAdapter() : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    private var quotesList: List<Quote> = arrayListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        lateinit var binding: QuoteItemBinding
        context = parent.context

        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.quote_item, parent, false)

        return ViewHolder(binding, this@QuotesAdapter)
    }

    override fun getItemCount(): Int = quotesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val androidColors: IntArray = context.resources.getIntArray(R.array.androidcolors)
        val randomColor = androidColors[Random().nextInt(androidColors.size)]


        holder.itemView.quote_card.setCardBackgroundColor(randomColor)


        holder.bind(quotesList[position], holder)
    }

    class ViewHolder(viewBinder: QuoteItemBinding, adapterReference: QuotesAdapter) : RecyclerView.ViewHolder(viewBinder.root) {

        private var binding: QuoteItemBinding = viewBinder
        private var adapter: QuotesAdapter = adapterReference

        //Binding with the ui item of the list
        fun bind(quote: Quote, viewHolder: ViewHolder) {

            binding.adapterReference = adapter
            binding.holder = viewHolder
            binding.quote = quote
            binding.executePendingBindings()
        }

    }

    /**
     * Activity uses this method to update with the help of LiveData
     * */
    fun setAllQuotes(quotesItems: List<Quote>) {
        this.quotesList = quotesItems
        notifyDataSetChanged()
    }

}
