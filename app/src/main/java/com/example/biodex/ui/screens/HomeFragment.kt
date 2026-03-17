package com.example.biodex.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.biodex.R
import com.example.biodex.core.BaseFragment
import com.example.biodex.core.extension.collectFlow
import com.example.biodex.databinding.FragmentHomeBinding
import com.example.biodex.ui.adapters.SightingAdapter
import com.example.biodex.ui.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sightingAdapter: SightingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initListeners()
        initObservers()

    }

    private fun setupRecyclerView() {
        sightingAdapter = SightingAdapter {sighting ->
            Timber.d("Click en: ${sighting.title}")
        }
        binding.rvSights.adapter = sightingAdapter
    }

    private fun initListeners() {
        binding.fabAddSight.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createSightingFragment)
        }
    }

    private fun initObservers() {
        collectFlow(viewModel.uiState) { state ->
            when (state) {
                is HomeViewModel.HomeUiState.Loading -> {
                    binding.progressIndicator.isVisible = true
                    binding.rvSights.isVisible = false
                    binding.llEmptyState.isVisible = false
                }
                is HomeViewModel.HomeUiState.Empty -> {
                    binding.rvSights.isVisible = false
                    binding.progressIndicator.isVisible = false
                }
                is HomeViewModel.HomeUiState.Success -> {
                    binding.llEmptyState.visibility = View.GONE
                    binding.rvSights.isVisible = true
                    binding.progressIndicator.isVisible = false
                    sightingAdapter.submitList(state.sightings)
                }
                is HomeViewModel.HomeUiState.Error -> {
                    binding.progressIndicator.isVisible = false
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG)
                        .setAction("Reintentar") {

                        }
                        .show()
                }
            }
        }
    }

}