package com.example.punnetverse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punnetverse.adapters.TemplateAdapter
import com.example.punnetverse.databinding.FragmentTemplatesBinding
import com.example.punnetverse.viewmodel.TemplatesViewModel

class TemplatesFragment : Fragment() {
    private lateinit var binding: FragmentTemplatesBinding
    private lateinit var viewModel: TemplatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTemplatesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[(TemplatesViewModel::class.java)]
        setListener()
        initRecyclerView()
        setObserver()
        return binding.root
    }

    private fun setListener() {
        //searching
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getSearchTemp(binding.etSearch.text.toString())
            }
            true
        }
        //to clear ediText
        binding.ivClear.setOnClickListener{
            binding.etSearch.text.clear()
        }
    }

    private fun initRecyclerView() {
        binding.rcv.layoutManager = LinearLayoutManager(context)
        viewModel.getMemeTemp()
    }

    private fun setObserver() {
        viewModel.tempList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) binding.rcv.adapter = TemplateAdapter(it)
            else Toast.makeText(context, "Please try again after some time!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = TemplatesFragment()
    }
}