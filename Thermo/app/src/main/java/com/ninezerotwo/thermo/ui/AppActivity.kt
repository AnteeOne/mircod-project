package com.ninezerotwo.thermo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.ui.auth.LoginFragment
import com.ninezerotwo.thermo.ui.auth.RegisterFragment
import com.ninezerotwo.thermo.ui.auth.TitleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}
