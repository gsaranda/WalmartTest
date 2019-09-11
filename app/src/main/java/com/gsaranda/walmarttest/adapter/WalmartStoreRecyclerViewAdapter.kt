package com.gsaranda.walmarttest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsaranda.walmarttest.R
import com.gsaranda.walmarttest.models.WalmartStoreModel

class WalmartStoreRecyclerViewAdapter(val onItemSelected:(WalmartStoreModel)->Unit): RecyclerView.Adapter<WalmartStoreRecyclerViewAdapter.StoreViewHolder>() {
    var tiendas:List<WalmartStoreModel>

    init {
        tiendas= emptyList()
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.tvName.text=tiendas.get(position).name
        holder.tvAddress.text=tiendas.get(position).address
        holder.vClickable.setOnClickListener{onItemSelected(tiendas.get(position))}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_stores, parent, false)
        return StoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tiendas.size
    }


    inner class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvAddress: TextView
        val vClickable:View


        init {
            tvName=view.findViewById(R.id.tv_item_name)
            tvAddress=view.findViewById(R.id.tv_item_address)
            vClickable=view.findViewById(R.id.v_item_store_clickable_section)
        }
    }


}