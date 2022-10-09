package com.thatnawfal.binarsibc5challange.ui.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountEntity
import com.thatnawfal.binarsibc5challange.databinding.FragmentProfileBinding
import com.thatnawfal.binarsibc5challange.provider.ServiceLocator
import com.thatnawfal.binarsibc5challange.wrapper.Resource


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

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
        }
    }
}