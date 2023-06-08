package com.davito.misseriesapp.ui.misseries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davito.misseriesapp.R
import com.davito.misseriesapp.databinding.CardViewSerieItemBinding
import com.davito.misseriesapp.model.Serie

class SeriesAdapter(
    private val seriesList: ArrayList<Serie>,
    private val onItemClicked: (Serie) -> Unit,
) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_serie_item, parent, false)
        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val serie = seriesList[position]
        holder.bind(serie)
        holder.itemView.setOnClickListener{ onItemClicked(seriesList[position])}
    }

    override fun getItemCount(): Int = seriesList.size

    fun appendItems(newList: ArrayList<Serie>){
        seriesList.clear()
        seriesList.addAll(newList)
        notifyDataSetChanged()
    }


    class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = CardViewSerieItemBinding.bind(itemView)

        fun bind(serie : Serie){
            with(binding){
                nameTextView.text = serie.name
                seasonTextView.text = "Season: ${serie.season}"
            }
        }
    }

}