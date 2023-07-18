package com.example.punnetverse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.punnetverse.R
import com.example.punnetverse.adapters.TemplateAdapter
import com.example.punnetverse.data.Template
import com.example.punnetverse.databinding.FragmentTemplatesBinding
import com.example.punnetverse.utils.ItemDecoration

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TemplatesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTemplatesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTemplatesBinding.inflate(inflater)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rcv.layoutManager = GridLayoutManager(context,2)
        binding.rcv.addItemDecoration(ItemDecoration(2, 2, 0))
        val sampleTempList = listOf(
            Template(1, R.drawable.psp2, ""),
            Template(1, R.drawable.psp2, ""),
            Template(1, R.drawable.psp2, ""),
            Template(1, R.drawable.psp2, ""),
            Template(1, R.drawable.psp2, "")
            ,Template(1, R.drawable.psp2, ""))
        binding.rcv.adapter = TemplateAdapter(sampleTempList)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TemplatesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}