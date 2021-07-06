package com.ninezerotwo.thermo.ui.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.databinding.FragmentRegisterBinding
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.ui.auth.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners(){
        binding.btnRegisterPerson.setOnClickListener{
            signUpViewModel.signUp(
                with(binding){
                    User(
                        email = etPersonEmailRegister.text.toString(),
                        username = etPersonNameRegister.text.toString(),
                        password = etPasswordRegister.text.toString(),
                        firstName = etFirstName.text.toString(),
                        lastName = etLastName.text.toString()
                    )
                }
            )
        }
    }

    private fun initObservers(){
        signUpViewModel.authStateLiveData.observe(viewLifecycleOwner){
            when(it){
                is SignUpViewModel.SignUpState.Empty -> {
                }
                is SignUpViewModel.SignUpState.Authenticated -> {
                    Snackbar.make(
                        binding.root,
                        "Account has been created!",
                        Snackbar.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
                is SignUpViewModel.SignUpState.Failed -> {
                    Snackbar.make(
                        binding.root,
                        "Authentication Failed! Please try again!",
                        Snackbar.LENGTH_SHORT)
                    .show()
                }
            }
        }
    }
}