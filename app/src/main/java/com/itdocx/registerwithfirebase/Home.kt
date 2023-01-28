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
import com.google.firebase.database.FirebaseDatabase
import com.itdocx.registerwithfirebase.Utils.users
import com.itdocx.registerwithfirebase.databinding.FragmentHomeBinding


class Home : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var dataBase: FirebaseDatabase
    private val emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        val view = binding.root


        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance()


        binding.btnSignup.setOnClickListener {

            val fname = binding.etSUFN.text.toString()
            val lname = binding.etSULN.text.toString()
            val email = binding.etSUEmail.text.toString()
            val phone = binding.etSUPhone.text.toString()
            val address = binding.etSUAddress.text.toString()
            val password = binding.etSUPassword.text.toString()
            val confirmPass = binding.etSUCP.text.toString()
            binding.SUprog.visibility = View.VISIBLE

            if (fname.isEmpty()|| lname.isEmpty()||email.isEmpty()||phone.isEmpty()
                || address.isEmpty() ||password.isEmpty()||confirmPass.isEmpty())
            {
                if(fname.isEmpty()){
                    binding.etSUFN.error = "Enter your name"

                }
                if(lname.isEmpty()){
                    binding.etSUFN.error = "Enter your Last name"

                }
                if(email.isEmpty()){
                    binding.etSUFN.error = "Enter your Email"

                }
                if(phone.isEmpty()){
                    binding.etSUFN.error = "Enter your Active Phone Number"

                }
                if(address.isEmpty()){
                    binding.etSUFN.error = "Enter your present Address"

                }
                if(password.isEmpty()){
                    binding.etSUFN.error = "Password required"


                }
                if(confirmPass.isEmpty()){
                    binding.etSUFN.error = "Retype password"

                }
                Toast.makeText(context,"Please Fill all fields",Toast.LENGTH_SHORT).show()
                binding.SUprog.visibility = View.GONE

            }
            else if (!email.matches(emailPattern.toRegex())){
                binding.SUprog.visibility = View.GONE
                binding.etSUEmail.error =  "Please enter valid E-mail address"
                Toast.makeText(context,"Please enter valid E-mail address",Toast.LENGTH_SHORT).show()
            }

            else if (binding.etSUPhone.length()!= 11|| !phone.contains("03")){
                binding.SUprog.visibility = View.GONE
                binding.etSUPhone.error =  "Please enter valid Phone Number"
                Toast.makeText(context,"Please enter valid Phone Number",Toast.LENGTH_SHORT).show()
            }
            else if( binding.etSUPassword.length() <= 6){
                binding.SUprog.visibility = View.GONE
                binding.etSUPassword.error = "Enter password more than 6 characters"
                Toast.makeText(context,"Enter password more than 6 characters",Toast.LENGTH_SHORT).show()
            }

            else if (password != confirmPass){
                binding.SUprog.visibility = View.GONE
                binding.etSUCP.error ="Password doesn't match"
                Toast.makeText(context,"Password doesn't match",Toast.LENGTH_SHORT).show()
            }

            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{

                    if (it.isSuccessful){
                        val dataBaseRef = dataBase.reference.child("users").child(auth.currentUser!!.uid)
                        val users:users = users(fname,email,phone,address ,auth.currentUser!!.uid)

                        dataBaseRef.setValue(users).addOnCompleteListener{

                            if (it.isSuccessful){
                                findNavController().navigate(R.id.action_home2_to_login)
                            }
                            else{
                                binding.SUprog.visibility = View.GONE
                                Toast.makeText(context,"Something went wrong Please try again later",Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                    else{
                        binding.SUprog.visibility = View.GONE
                        Toast.makeText(context,"Something went wrong Please try again later",Toast.LENGTH_SHORT).show()
                    }
                }

            }



        }


        return view
    }
}