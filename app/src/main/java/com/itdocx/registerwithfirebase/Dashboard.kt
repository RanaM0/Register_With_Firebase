package com.itdocx.registerwithfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.itdocx.registerwithfirebase.databinding.FragmentDashboardBinding


class Dashboard : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false)


        binding.btnLogout.setOnClickListener {
             FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_dashboard_to_login)
            onDetach()
        }



        return binding.root
    }


}