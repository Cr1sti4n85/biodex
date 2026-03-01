package com.example.biodex.ui.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.biodex.R
import com.example.biodex.core.BaseFragment
import com.example.biodex.core.extension.collectFlow
import com.example.biodex.databinding.FragmentCreateSightingBinding
import com.example.biodex.ui.viewmodel.CreateSightingViewModel
import com.example.biodex.ui.viewmodel.state.CreateSightingUiEvent
import com.example.biodex.ui.viewmodel.state.CreateSightingUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class CreateSightingFragment : BaseFragment<FragmentCreateSightingBinding>(
    FragmentCreateSightingBinding::inflate
) {
    private val viewModel: CreateSightingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners(){
        with(binding) {
            etTitle.doAfterTextChanged { text ->
                viewModel.onTitleChanged(text.toString())

            }

            etDescription.doAfterTextChanged { text ->
                viewModel.onDescriptionChanged(text.toString())
            }

            btnSubmit.setOnClickListener {
                viewModel.submit()
                hideKeyboard()
            }

            cvPhotoContainer.setOnClickListener {
                viewModel.onPhotoSelected("https://i.pinimg.com/736x/7f/af/b9/7fafb9d4b589a67f9115f9a258a2b4a4.jpg".toUri())
            }
        }
    }

    private fun initObservers() {
        collectFlow(viewModel.uiState) { state ->
            renderState(state)
        }

        collectFlow(viewModel.uiEvent) { event ->
            handleEvent(event)
        }
    }

    private fun renderState(state: CreateSightingUiState) {
        with(binding) {
            btnSubmit.isEnabled = !state.isLoading && state.isFormValid
            btnSubmit.text = if (state.isLoading) "Enviando..." else "Enviar registro"

            tilTitle.error = state.titleError?.let { getString(it) }

            if (state.photoUri != null) {
                ivSightPhoto.isVisible = true
                llPhotoPlaceholder.isVisible = false
            }
        }
    }

    private fun handleEvent(event: CreateSightingUiEvent) {
        when(event) {
            is CreateSightingUiEvent.ShowError -> {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            }
            CreateSightingUiEvent.SuccessNavigation -> {
                Toast.makeText(requireContext(), "¡Registro creado!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}