package com.ninezerotwo.thermo.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.usecases.auth.SignUpUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val signUpUsecase: SignUpUsecase
) : ViewModel() {

    sealed class SignUpState {
        object Empty : SignUpState()
        object Failed : SignUpState()
        data class Authenticated(val user: User) : SignUpState()
    }

    private var _authStateLiveData = MutableLiveData<SignUpState>()
    val authStateLiveData: LiveData<SignUpState> get() = _authStateLiveData

    fun signUp(user: User) {
        signUpUsecase.invoke(viewModelScope, user) {
            when (it) {
                is Outcome.Success -> {
                    _authStateLiveData.postValue(
                        SignUpState.Authenticated(it.data)
                    )
                    Log.d("apptag","Response user: ${it.data}")
                }
                is Outcome.Failure -> {
                    _authStateLiveData.postValue(
                        SignUpState.Failed
                    )
                    Log.d("apptag","Response user: Failure")
                }
            }
        }
    }

}