package com.riyaspullur.stateflowconcept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.riyaspullur.stateflowconcept.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private val viewModel:MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {

            viewModel.login(binding.userNameInputId.text.toString(),binding.passwordInputId.text.toString())

        }

        lifecycleScope.launchWhenStarted {

            viewModel.loginUiState.collect{
                when(it){
                    is MainViewModel.LoginUiState.Success->{
                        Snackbar.make(binding.root,"succesfully launched",Snackbar.LENGTH_LONG).show()
                        binding.progressbarID.isVisible=false
                    }
                    is MainViewModel.LoginUiState.Error->{
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_LONG).show()
                        binding.progressbarID.isVisible=false
                    }
                    is MainViewModel.LoginUiState.Loading->{
                        binding.progressbarID.isVisible=true
                    }
                    else->{
                        Unit
                    }
                }
            }
        }
    }
}