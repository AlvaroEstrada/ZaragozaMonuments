package com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.databinding.ItemMonumentBinding
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnClickItemListener
import com.squareup.picasso.Picasso

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters.holders
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class MonumentsViewHolder(view: View, private val onClickItemListener: OnClickItemListener): RecyclerView.ViewHolder(view) {

    private val binding = ItemMonumentBinding.bind(view)

    fun bind(monument: Monument){
        Picasso.get().load(monument.image).into(binding.imgMonument)
        binding.titleMonuments.text = monument.title
        binding.btnLocate.setOnClickListener{
            onClickItemListener.locateItem(monument)
        }
        binding.cvRoot.setOnClickListener{
            onClickItemListener.detailsItem(monument, true)
        }
    }
}