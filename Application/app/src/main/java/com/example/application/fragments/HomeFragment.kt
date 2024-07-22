package com.example.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.adapter.RecyclerViewAdapter
import com.example.application.databinding.FragmentHomeBinding
import com.example.application.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val adapter = RecyclerViewAdapter(emptyList())

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collect { results ->
                adapter.setData(results)
            }
        }

        binding.button.setOnClickListener {
            if (binding.editText.toString() != "") {
                viewModel.getSearchResults(binding.editText.text.toString())
            } else {
                Toast.makeText(activity, "Enter something", Toast.LENGTH_LONG).show()
            }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (binding.editText.toString().isNotBlank()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.getSearchResults(binding.editText.text.toString())
                    }
                }
            }
        })

        return binding.root
    }
}