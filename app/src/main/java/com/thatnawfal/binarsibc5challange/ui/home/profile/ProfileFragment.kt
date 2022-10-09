package com.thatnawfal.binarsibc5challange.ui.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountEntity
import com.thatnawfal.binarsibc5challange.databinding.FragmentProfileBinding
import com.thatnawfal.binarsibc5challange.provider.ServiceLocator
import com.thatnawfal.binarsibc5challange.ui.MainActivity
import com.thatnawfal.binarsibc5challange.wrapper.Resource


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var account : AccountEntity

    private val viewModel: ProfileViewModel by lazy {
        ProfileViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataUser(viewModel.getIdPreference().toString().toInt())
        observeAction()

        binding.btnConfirm.setOnClickListener { submitForm() }
        binding.btnLogout.setOnClickListener {
            viewModel.setIdPreference(0)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun observeAction() {
        viewModel.userDetailResult.observe(requireActivity()){
            when(it){
                is Resource.Success -> {
                    bindingView(it.payload)
                }
            }
        }
    }

    private fun bindingView(userData: AccountEntity?) {
        userData?.let {
            binding.textView.text = "Hi, ${it.username}"
            binding.etRegUsername.setText(it.username)
            binding.etRegEmail.setText(it.email)
            account = it
        }
    }

    private fun setFormEnabled(b: Boolean) {
        with(binding) {
            etRegUsername.isEnabled = b
            etRegEmail.isEnabled = b
            etRegPass.isEnabled = b
            etRegNewPass.isEnabled = b
        }
    }

    private fun formValidate() : Boolean{
        val username = binding.etRegUsername.text.toString()
        val email = binding.etRegEmail.text.toString()
        val password = binding.etRegPass.text.toString()
        val newPass = binding.etRegNewPass.text.toString()
        var validateForm = true

        if (username.isEmpty()){
            validateForm = false
            binding.tilEtRegUsername.isErrorEnabled = true
            binding.tilEtRegUsername.error = "Username Can't Be Empty"
        } else { binding.tilEtRegUsername.isErrorEnabled = false }

        if (email.isEmpty()){
            validateForm = false
            binding.tilEtRegEmail.isErrorEnabled = true
            binding.tilEtRegEmail.error = "Email Can't Be Empty"
        } else {
            binding.tilEtRegEmail.isErrorEnabled = false
        }

        if (password.isEmpty()){
            validateForm = false
            binding.tilEtRegPass.isErrorEnabled = true
            binding.tilEtRegPass.error = "Password Can't Be Empty"
        } else {
            if (password == account.password) {
                binding.tilEtRegPass.isErrorEnabled = false
            } else {
                validateForm = false
                binding.tilEtRegPass.isErrorEnabled = true
                binding.tilEtRegPass.error = "Not Your Password"
            }
        }

        if (newPass.isEmpty()){
            validateForm = false
            binding.tilEtRegNewPass.isErrorEnabled = true
            binding.tilEtRegNewPass.error = "New Password Can't Be Empty"
        } else {
            validateForm = false
            if (newPass == password) {
                validateForm = false
                binding.tilEtRegNewPass.isErrorEnabled = true
                binding.tilEtRegNewPass.error = "Don't Use The Same Password"
            } else {
                binding.tilEtRegNewPass.isErrorEnabled = false
                validateForm = true
            }
        }

        return validateForm
    }

    private fun getEntityDataFromForm(): AccountEntity {
        return AccountEntity(
            id = viewModel.getIdPreference().toString().toInt(),
            username = binding.etRegUsername.text.toString(),
            email = binding.etRegEmail.text.toString(),
            password = binding.etRegNewPass.text.toString()
        )
    }

    private fun submitForm(){
        if (formValidate()) {
            Toast.makeText(requireContext(), binding.etRegUsername.text.toString(), Toast.LENGTH_SHORT).show()
            viewModel.updateAccount(getEntityDataFromForm())
            findNavController().popBackStack()
        }
    }
}