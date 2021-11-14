package com.alvaroestrada.zaragozamonuments.ui.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvaroestrada.zaragozamonuments.R
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.databinding.FragmentMonumentsBinding
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnClickItemListener
import com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters.MonumentsAdapter
import android.graphics.drawable.InsetDrawable
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnMainActivityListener


class MonumentsFragment(context: Context, private var mainListener: OnMainActivityListener, var monumentList: List<Monument>) : DialogFragment(), OnClickItemListener {

    private var _binding: FragmentMonumentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MonumentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonumentsBinding.inflate(inflater, container, false)
        setupUI()
        setupButtons()
        initRecyclerView()
        return binding.root
    }

    override fun locateItem(monument: Monument) {
        mainListener.locateMonument(monument)
    }

    override fun detailsItem(monument: Monument, locateVisible: Boolean) {
        mainListener.detailsMonument(monument, locateVisible)
    }

    private fun setupUI() {
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 0, 60, 0, 60)
        dialog?.window?.setBackgroundDrawable(inset)
        dialog?.setCancelable(false)

    }

    private fun setupButtons() {
        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding
    }

    private fun initRecyclerView() {
        adapter = MonumentsAdapter(monumentList, this)
        binding.rvMonuments.layoutManager = LinearLayoutManager(context)
        binding.rvMonuments.adapter = adapter
    }
}