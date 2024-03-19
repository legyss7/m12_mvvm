package com.hw12

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hw12.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener {
            viewModel.searchData(binding.editTextSearch.text.toString())
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchText(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state ->
                        when (state) {
                            State.Waiting -> {
                                binding.buttonSearch.isEnabled = false
                                binding.progress.isVisible = false
                            }

                            State.Ready -> {
                                binding.buttonSearch.isEnabled = true
                                binding.progress.isVisible = false
                            }

                            State.Search -> {
                                binding.buttonSearch.isEnabled = false
                                binding.progress.isVisible = true
                            }

                            State.Success -> {
                                binding.buttonSearch.isEnabled = true
                                binding.progress.isVisible = false
                            }
                        }
                    }
            }

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { result ->
            binding.textView.text = result
        })
    }
}