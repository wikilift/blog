package com.wikilift.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController

import com.google.firebase.auth.FirebaseAuth
import com.wikilift.blogapp.R
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.data.remote.auth.AuthDataSource
import com.wikilift.blogapp.databinding.FragmentLoginBinding

import com.wikilift.blogapp.domain.auth.AuthRepoImpl
import com.wikilift.blogapp.presentation.auth.AuthViewModel

import com.wikilift.blogapp.presentation.auth.AuthViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    //instancia del viemodel inyección de dependendias
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(
        AuthDataSource() ))  }

    //by lazy se inicializa de esta manera y es ese tipo de dato
    //by lazy cuando intentes acceder a esta variable va a revisar
    //si ya existe una instancia creada, si es nula o no está la va a iniciar así,
    // solo en el momento q se utiliza
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    // private lateinit var firebaseAtuh2:FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        // firebaseAtuh2= FirebaseAuth.getInstance()
        isUserLoggedIn()
        doLogin()
        goToSingUpPage()


    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {   //elimina los espacios en blanco delante y detras
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.password.text.toString().trim()
            validateCredentials(email,password)
            signIn(email,password)
        }
    }
    private fun goToSingUpPage(){
        binding.txtSignup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun isPasswordValid(password: String): Boolean {
        val PASSWORD_REGEX =
            """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""".toRegex()
        return PASSWORD_REGEX.matches(password)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateCredentials(email: String, password: String) {
        if (!isEmailValid(email)) {
            binding.editTextEmail.requestFocus()
            binding.editTextEmail.error = "Revise el email"
            return
        }
        if (!isPasswordValid(password)) {
            binding.password.requestFocus()
            binding.password.error = "la contraseña debe ser segura"
            return
        }
    }
    private fun signIn(email:String,password:String){
        viewModel.signIn(email,password).observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Result.Loading->{
                    binding.progressBar.visibility=View.VISIBLE
                    binding.btnSignin.isEnabled=false

                }
                is Result.Succes->{

                    binding.progressBar.visibility=View.GONE


                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    Toast.makeText(context,"Welcome ${result.data?.email}",Toast.LENGTH_SHORT).show()
                }
                is Result.Failure->{
                    binding.progressBar.visibility=View.GONE
                    binding.btnSignin.isEnabled=true
                    Toast.makeText(requireContext(),"${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}