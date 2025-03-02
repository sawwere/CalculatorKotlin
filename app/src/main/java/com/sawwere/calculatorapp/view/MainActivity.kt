package com.sawwere.calculatorapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sawwere.calculatorapp.R
import com.sawwere.calculatorapp.databinding.ActivityMainBinding
import com.sawwere.calculatorapp.veiwmodel.Calculator
import com.sawwere.calculatorapp.veiwmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var viewModel: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = CalculatorViewModel()

        mainBinding.viewModel = viewModel
    }
}