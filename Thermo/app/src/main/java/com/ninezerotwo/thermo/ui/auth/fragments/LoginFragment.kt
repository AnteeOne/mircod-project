package com.ninezerotwo.thermo.ui.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.databinding.FragmentLoginBinding
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.ui.auth.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.btnLoginPerson.setOnClickListener {
            signInViewModel.signIn(
                with(binding) {
                    User(
                        username = etPersonNameLogin.text.toString(),
                        password = etPasswordLogin.text.toString()
                    )
                }
            )
        }
    }

    private fun initObservers() {
        signInViewModel.authStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SignInViewModel.SignInState.Empty -> {
                }
                is SignInViewModel.SignInState.Authenticated -> {
                    Snackbar.make(
                        binding.root,
                        "Sign is as ${it.userToken}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is SignInViewModel.SignInState.Failed -> {
                    Snackbar.make(
                        binding.root,
                        "Authentication Failed! Please try again!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}