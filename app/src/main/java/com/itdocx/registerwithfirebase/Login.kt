package com.itdocx.registerwithfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.itdocx.registerwithfirebase.Utils.users
import com.itdocx.registerwithfirebase.databinding.FragmentLoginBinding


class Login : Fragment() {

    private val emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var binding:FragmentLoginBinding
    private lateinit var authLogin:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)


        binding.btnSignin.setOnClickListener {

            binding.SIprog.visibility = View.VISIBLE

            authLogin = FirebaseAuth.getInstance()
            val emailLogin = binding.etSIEmail.text.toString()
            val passLogin = binding.etSIPassword.text.toString()

            if (emailLogin.isEmpty() || passLogin.isEmpty()) {
                if (emailLogin.isEmpty()) {
                    binding.etSIEmail.error = "Please enter Email"
                }
                if (passLogin.isEmpty()) {
                    binding.etSIPassword.error = "Please enter Password"
                }
                binding.SIprog.visibility = View.GONE
                Toast.makeText(context, "Please enter required credentials", Toast.LENGTH_SHORT).show()
            } else if (!emailLogin.matches(emailPattern.toRegex())) {
                binding.SIprog.visibility = View.GONE
                binding.etSIEmail.error = "Please enter valid E-mail address"
                Toast.makeText(context, "Please enter valid E-mail address", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etSIPassword.length() <= 6) {
                binding.SIprog.visibility = View.GONE
                binding.etSIPassword.error = "Enter password more than 6 characters"
                Toast.makeText(context, "Enter password more than 6 characters", Toast.LENGTH_SHORT)
                    .show()
            } else {
                authLogin.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener {

                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.action_login_to_dashboard)
                        binding.SIprog.visibility = View.GONE

                    }


                    else {
                        Toast.makeText(
                            context,
                            "User doesn't exist",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.SIprog.visibility = View.GONE
                    }


                }

            }
        }


        binding.btnSignUpLogin.setOnClickListener {

            findNavController().navigate(R.id.action_login_to_home2)

        }



        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null){

            findNavController().navigate(R.id.action_login_to_dashboard)
            onDetach()
        }

    }


}