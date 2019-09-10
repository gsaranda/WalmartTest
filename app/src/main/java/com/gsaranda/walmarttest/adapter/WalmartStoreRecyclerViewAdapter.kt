package com.gsaranda.walmarttest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gsaranda.walmarttest.R

class WalmartStoreRecyclerViewAdapter: RecyclerView.Adapter<WalmartStoreRecyclerViewAdapter.StoreViewHolder>() {


    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.rv_item_stores, parent, false)
        return StoreViewHolder(view)
    }

    override fun getItemCount(): Int {

        return 0
    }


    inner class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvAddress: TextView


        init {
            tvName=view.findViewById(R.id.tv_item_name)
            tvAddress=view.findViewById(R.id.tv_item_address)
        }
    }


}