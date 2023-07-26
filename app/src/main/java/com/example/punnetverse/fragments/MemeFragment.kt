package com.example.punnetverse.fragments

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.punnetverse.R
import com.example.punnetverse.databinding.FragmentMemeBinding
import com.example.punnetverse.responsedata
import com.example.punnetverse.retrofit_instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MemeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMemeBinding

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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMemeBinding.inflate(inflater)
        getData()

        binding.button.setOnClickListener {
            getData()
        }
        return binding.root
    }

    private fun getData() {

        val progressDialog = ProgressDialog(this.context)
        progressDialog.setMessage("PLease wait while fetching data")
        progressDialog.show()

        retrofit_instance.apiInterface.getdata().enqueue(object : Callback<responsedata?> {
            override fun onResponse(call: Call<responsedata?>, response: Response<responsedata?>) {
                Glide.with(this@MemeFragment).load(response.body()?.url).into(binding.imageView)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<responsedata?>, t: Throwable) {
                Toast.makeText(requireActivity(),"error", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MemeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}