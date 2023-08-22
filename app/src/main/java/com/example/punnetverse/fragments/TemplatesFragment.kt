package com.example.punnetverse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.punnetverse.adapters.TemplateAdapter
import com.example.punnetverse.databinding.FragmentTemplatesBinding
import com.example.punnetverse.viewmodel.TemplatesViewModel
import kotlin.properties.Delegates

class TemplatesFragment : Fragment() {
    private lateinit var binding: FragmentTemplatesBinding
    private lateinit var viewModel: TemplatesViewModel
    private  var isTempFrag : Boolean = true
    private var isScrolling = false
    private var currItems by Delegates.notNull<Int>()
    private var totalItems by Delegates.notNull<Int>()
    private var scrollOutItems by Delegates.notNull<Int>()
    private var currPageNumber = 0
    private var isSearchType = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTemplatesBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[(TemplatesViewModel::class.java)]
        binding.collapsingToolbar.isVisible = isTempFrag
        setListener()
        initRecyclerView()
        setObserver()
        return binding.root
    }

    private fun setListener() {
        //searching
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                isSearchType = true
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
        //init rcv
        binding.rcv.layoutManager = LinearLayoutManager(context)
        binding.rcv.adapter = TemplateAdapter(arrayListOf())
        //data fetching
        if (isTempFrag) viewModel.getMemeTemp(currPageNumber++) else viewModel.getTrendingMemeTemp(currPageNumber++)
        binding.rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //scroll start
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)  isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //scroll end
                //getting all the position from rcv
                currItems = (binding.rcv.layoutManager as LinearLayoutManager).childCount
                totalItems = (binding.rcv.layoutManager as LinearLayoutManager).itemCount
                scrollOutItems = (binding.rcv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                //pagination condition-> if currPosition is the last position the fetch data
                if (isScrolling && (currItems + scrollOutItems == totalItems)) {
                    isScrolling = false
                    if (isTempFrag) viewModel.getMemeTemp(currPageNumber++) else viewModel.getTrendingMemeTemp(currPageNumber++)
                }
            }
        })
    }

    private fun setObserver() {
        viewModel.tempList.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                if (isSearchType) { //if we search some thing then just load new list
                    isSearchType = false
                    binding.rcv.adapter = TemplateAdapter(it)
                } else (binding.rcv.adapter as TemplateAdapter).updateList(it) //if we normal load data just add new data to the list
            }
            else if(it.isEmpty() && binding.etSearch.text.isNotEmpty()) Toast.makeText(context, "Please try again after some time!", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isTemplateFrag: Boolean, param2: String) = TemplatesFragment().apply {
            isTempFrag = isTemplateFrag
        }
    }
}