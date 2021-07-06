package com.ninezerotwo.thermo.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.usecases.auth.SignInUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInUsecase: SignInUsecase
): ViewModel() {

    sealed class SignInState {
        object Empty : SignInState()
        object Failed : SignInState()
        data class Authenticated(val userToken: String) : SignInState()
    }

    private var _authStateLiveData = MutableLiveData<SignInState>()
    val authStateLiveData: LiveData<SignInState> get() = _authStateLiveData

    fun signIn(user: User){
        signInUsecase.invoke(viewModelScope,user){
            when(it){
                is Outcome.Success -> {
                    _authStateLiveData.postValue(
                        SignInState.Authenticated(it.data)
                    )
                    Log.d("apptag","Response token: ${it.data}")
                }
                is Outcome.Failure -> {
                    _authStateLiveData.postValue(
                        SignInState.Failed
                    )
                    Log.d("apptag","Response token: Failed")
                }
            }
        }
    }
}