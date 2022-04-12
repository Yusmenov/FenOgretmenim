package com.erkankaraduman.fenogretmenm.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.erkankaraduman.fenogretmenm.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import org.w3c.dom.Text


class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firebaseAuth= FirebaseAuth.getInstance()
        initView()

    }
    private fun initView(){
        login_btn.setOnClickListener(this)
        tw_signup.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            login_btn->{
                validateData()
            }
            tw_signup->{
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }
    }

    private fun validateData() {
        email=et_email.text.toString().trim()
        password=et_password.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.error="Lütfen e-mail adresinizi kontrol ediniz."
        }else if (TextUtils.isEmpty(password)){
            et_password.error="Lütfen şifreniniz giriniz."
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            val firebaseUser=firebaseAuth.currentUser
            val email=firebaseUser?.email
            Toast.makeText(requireContext(),"Hoşgeldin: $email",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
        }.addOnFailureListener{
            Toast.makeText(requireContext(),"Giriş başarısız oldu.",Toast.LENGTH_SHORT).show()
        }
    }
}