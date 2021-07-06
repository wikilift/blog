package com.wikilift.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wikilift.blogapp.R
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.core.auth.CompareLogin
import com.wikilift.blogapp.data.remote.auth.AuthDataSource
import com.wikilift.blogapp.databinding.FragmentRegisterBinding
import com.wikilift.blogapp.domain.auth.AuthRepoImpl
import com.wikilift.blogapp.presentation.auth.AuthViewModel
import com.wikilift.blogapp.presentation.auth.AuthViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register), View.OnFocusChangeListener {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var userName: String
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()

    }


    private fun signUp() {

        binding.txtEmail.onFocusChangeListener = this
        binding.txtUserName.onFocusChangeListener = this
        binding.txtPassword.onFocusChangeListener = this
        binding.txtConfirmPassword.onFocusChangeListener = this


        binding.btnSignUp.setOnClickListener {
            email = binding.txtEmail.text.toString().trim()
            password = binding.txtPassword.text.toString().trim()
            confirmPassword = binding.txtConfirmPassword.text.toString().trim()
            userName = binding.txtUserName.text.toString().trim()
            if (password != confirmPassword || CompareLogin.isBlankField(password) ||
                !CompareLogin.isEmailValid(email)
            ) {
                return@setOnClickListener
            }

            createUser(email,password,userName)
            Log.d("signUpData", "data: $userName $password $confirmPassword $email ")
        }


    }

    private fun createUser(email: String, password: String, userName: String) {
        viewModel.signUp(email,password,userName).observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Result.Loading->{
                    binding.progressBar.visibility=View.VISIBLE
                    binding.btnSignUp.isEnabled=false
                }
                is Result.Succes->{
                    binding.progressBar.visibility=View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_setupProfileFragment)
                    Toast.makeText(requireContext(),"Bienvenido ${result.data?.email}",Toast.LENGTH_SHORT).show()
                }
                is Result.Failure->{
                    binding.progressBar.visibility=View.GONE
                    binding.btnSignUp.isEnabled=true
                    Toast.makeText(context,"${result.exception}",Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    override fun onFocusChange(v: View?, b: Boolean) {
        when (v) {
            binding.txtEmail -> {
                email = binding.txtEmail.text.toString().trim()
                if (!b) {
                    if (!CompareLogin.isEmailValid(email)) {
                        binding.txtEmail.error = "debe introducir un email válido"
                    }
                }
            }
            binding.txtUserName -> {
                userName = binding.txtUserName.text.toString().trim()
                if (!b) {
                    if (CompareLogin.isBlankField(userName))
                        binding.txtUserName.error = "Debe ingresar un nombre de usuario"
                }
            }
            binding.txtPassword -> {
                password = binding.txtPassword.text.toString().trim()
                if (!b) {
                    if (!CompareLogin.isPasswordValid(password)) {
                        binding.txtPassword.error = "Debe introducir una contraseña segura"
                    }
                }
            }
            binding.txtConfirmPassword -> {
                password = binding.txtPassword.text.toString().trim()
                confirmPassword = binding.txtConfirmPassword.text.toString().trim()
                if (!b) {
                    if (!CompareLogin.isPasswordEqual(password, confirmPassword)) {

                        binding.txtConfirmPassword.error = "Las constraseñas no coinciden"

                    }
                }
            }
        }
    }

}