package com.alvaroestrada.zaragozamonuments.ui.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.alvaroestrada.zaragozamonuments.R
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.databinding.FragmentMonumentsDetailsBinding
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnMainActivityListener
import com.alvaroestrada.zaragozamonuments.ui.view.fragments.adapters.MonumentsAdapter
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso


class MonumentsFragmentDetails(context: Context, private var monument: Monument, private var mainListener: OnMainActivityListener, private var locateVisible: Boolean) : DialogFragment() {

    private var _binding: FragmentMonumentsDetailsBinding? = null
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
        _binding = FragmentMonumentsDetailsBinding.inflate(inflater, container, false)
        setupUI()
        setupButtons()
        return binding.root
    }

    private fun setupUI() {
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 0, 60, 0, 60)
        dialog?.window?.setBackgroundDrawable(inset)
        dialog?.setCancelable(false)

        Picasso.get().load(monument.image).into(binding.imgMonument)
        binding.tvTitle.text = monument.title
        binding.tvDescription.text = monument.description
        binding.tvSchedule.text = monument.horario

        val radius = resources.getDimension(R.dimen.margin_default)
        val shapeAppearanceModel = binding.imgMonument.shapeAppearanceModel.toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED,radius)
            .setTopLeftCorner(CornerFamily.ROUNDED,radius)
            .build()
        binding.imgMonument.shapeAppearanceModel = shapeAppearanceModel

        if (locateVisible){
            binding.locateBtn.visibility = View.VISIBLE
        }else{
            binding.locateBtn.visibility = View.GONE
        }

    }

    private fun setupButtons() {
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
        binding.locateBtn.setOnClickListener{
            mainListener.locateMonument(monument)
        }
    }
}