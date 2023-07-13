package com.example.punnetverse

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.punnetverse.adapters.TemplateAdapter
import com.example.punnetverse.data.Template
import com.example.punnetverse.databinding.FragmentTemplatesBinding

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
        val sampleTempList = listOf(
            Template(1, R.drawable.pspng, ""),
            Template(1, R.drawable.pspng, ""),
            Template(1, R.drawable.pspng, ""),
            Template(1, R.drawable.pspng, ""),
            Template(1, R.drawable.pspng, "")
            ,Template(1, R.drawable.pspng, ""))
        binding.rcv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position: Int = parent.getChildAdapterPosition(view) // item position

                val spanCount = 2
                val spacing = 10 //spacing between views in grid


                if (position >= 0) {
                    val column = position % spanCount // item column
                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        })
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