package com.ninezerotwo.thermo.ui.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninezerotwo.thermo.domain.usecases.auth.GetTokenUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TitleViewModel @Inject constructor(
    private val getTokenUsecase: GetTokenUsecase
): ViewModel() {

    sealed class TokenState {
        object Empty : TokenState()
        object Failed : TokenState()
        object Authenticated : TokenState()
    }

    private var _tokenStateLiveData = MutableLiveData<TokenState>()
    val tokenStateLiveData: LiveData<TokenState> get() = _tokenStateLiveData

    fun checkTokenState(){
        getTokenUsecase.invoke(viewModelScope,Unit){
            when(it){
                is Outcome.Failure -> {
                    _tokenStateLiveData.postValue(TokenState.Failed)
                }
                is Outcome.Success -> {
                    _tokenStateLiveData.postValue(TokenState.Authenticated)
                }
            }
        }
    }
}