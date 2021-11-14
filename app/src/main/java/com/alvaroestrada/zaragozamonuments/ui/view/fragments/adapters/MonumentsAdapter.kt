package com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvaroestrada.zaragozamonuments.R
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnClickItemListener
import com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters.holders.MonumentsViewHolder

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class MonumentsAdapter(val monuments: List<Monument>, val onClickItemListener: OnClickItemListener): RecyclerView.Adapter<MonumentsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MonumentsViewHolder(layoutInflater.inflate(R.layout.item_monument, parent, false), onClickItemListener)
    }

    override fun onBindViewHolder(holder: MonumentsViewHolder, position: Int) {
        val item = monuments[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = monuments.size
}