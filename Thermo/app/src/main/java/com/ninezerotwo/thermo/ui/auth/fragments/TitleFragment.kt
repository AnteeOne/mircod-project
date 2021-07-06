package com.ninezerotwo.thermo.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentTitleBinding
import com.ninezerotwo.thermo.ui.auth.viewmodels.TitleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleFragment : Fragment() {

    private var _binding: FragmentTitleBinding? = null
    private val binding get() = _binding!!

    private val titleViewModel: TitleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTitleBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        checkSession()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_titleFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_titleFragment_to_registerFragment)
        }
    }

    private fun initObservers() {
        titleViewModel.tokenStateLiveData.observe(viewLifecycleOwner) {
            Log.d("apptag", "Token observers init...")
            when (it) {
                TitleViewModel.TokenState.Authenticated -> {
                    Snackbar.make(
                        binding.root,
                        "Signed in by token!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                TitleViewModel.TokenState.Empty -> {
                }
                TitleViewModel.TokenState.Failed -> {
                }
            }
        }
    }

    private fun checkSession() {
        titleViewModel.checkTokenState()
    }
}