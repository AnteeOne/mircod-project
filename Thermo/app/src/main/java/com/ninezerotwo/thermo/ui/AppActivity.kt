package com.ninezerotwo.thermo.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.data.network.AuthApi
import com.ninezerotwo.thermo.data.network.dto.UserSignUpDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    @Inject
    lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }

    override fun onStart() {
        super.onStart()
        runBlocking {
            val response = authApi.signUp(
                UserSignUpDto(
                    "sdfds@sdf.er",
                    "sdfs",
                    "sdfsef",
                    "sdfsf",
                    "sdf234"
                )
            )
            Log.d("antee-tag",response.toString())
        }
    }
}
