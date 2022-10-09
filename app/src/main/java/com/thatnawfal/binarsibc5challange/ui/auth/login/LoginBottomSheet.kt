package com.thatnawfal.binarsibc5challange.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.databinding.FragmentLoginBottomSheetBinding
import com.thatnawfal.binarsibc5challange.provider.ServiceLocator
import com.thatnawfal.binarsibc5challange.ui.home.HomeActivity

class LoginBottomSheet(private var listener: OnLoginListener) : BottomSheetDialogFragment()  {

    private lateinit var binding : FragmentLoginBottomSheetBinding

    private val viewModel: LoginViewModel by lazy {
        LoginViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginValidation()

        isCancelable = false

        binding.btnLoginLogin.setOnClickListener {
            if (formValidation()) {
                dismiss()
                succesLogin()
            }

        }

        binding.btnLoginRegister.setOnClickListener {
            dismiss()
            listener.toRegister()
        }
    }
    private fun succesLogin() {
        viewModel.getIdFromEmail(binding.etLoginEmail.text.toString())
        viewModel.idFromEmail.observe(requireActivity()) {
            viewModel.setIdPreference(it)
            listener.loginSuccess()
        }

    }

    private fun loginValidation() {
        if (viewModel.getIdPreference() != 0) {
            listener.loginSuccess()
        }
    }

    private fun formValidation (): Boolean {
        val email = binding.etLoginEmail.text.toString()
        val pass = binding.etLoginPassword.text.toString()
        var validateForm = true

        if (email.isEmpty()) {
            validateForm = false
            binding.tilEtLoginEmail.isErrorEnabled = true
            binding.tilEtLoginEmail.error = "Don't Empty The Field"
        } else { binding.tilEtLoginEmail.isErrorEnabled = false }

        if (pass.isEmpty()) {
            validateForm = false
            binding.tilEtLoginPassword.isErrorEnabled = true
            binding.tilEtLoginPassword.error = "Don't Empty The Field"
        } else {
            validateForm = false
            viewModel.checkEmailPassword(email, pass)
            viewModel.isPassCorrect.observe(requireActivity()) {
                if (!it) {
                    binding.tilEtLoginPassword.isErrorEnabled = true
                    binding.tilEtLoginPassword.error = "Check Your Email & Password!"
                } else {
                    validateForm = true
                    binding.tilEtLoginPassword.isErrorEnabled = false
                }
            }
        }
        return validateForm
    }

}

interface OnLoginListener {
    fun toRegister()
    fun loginSuccess()

}