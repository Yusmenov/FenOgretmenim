package com.erkankaraduman.fenogretmenm.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.erkankaraduman.fenogretmenm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var email = ""
    private var password = ""
    private var firstName = ""
    private var lastName = ""
    private var confirmPassword = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        databaseReference= FirebaseDatabase.getInstance().getReference()
        initView()
    }

    private fun initView() {
        sign_up_button.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            sign_up_button->{
               validateData()
            }

        }
    }

    private fun validateData() {
        firstName=et_name.text.toString().trim()
        lastName=et_surname.text.toString().trim()
        email=et_email.text.toString().trim()
        password=et_password.text.toString().trim()
        confirmPassword=et_password_again.text.toString().trim()
        if(firstName.isEmpty() && lastName.isEmpty()){
            Toast.makeText(requireContext(),"Lütfen adınızı veya soyadınızı kontrol ediniz.",Toast.LENGTH_SHORT).show()
        }else if(password.isEmpty()){
            Toast.makeText(requireContext(),"Lütfen şifrenizi yazınız",Toast.LENGTH_SHORT).show()
        }else if(confirmPassword.isEmpty()){
            Toast.makeText(requireContext(),"Şifrenizi tekrar giriniz.",Toast.LENGTH_SHORT).show()
        }else if(password != confirmPassword){
            Toast.makeText(requireContext(),"Şifreler eşleşmiyor",Toast.LENGTH_SHORT).show()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.error = "e-mail formatını kontrol ediniz"
        }else if(password.length < 6) {
            et_password.error = "Şifreniz en az 6 karakter olmalıdır."
        }else {
            createUserAccount()
        }

    }

    private fun createUserAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            updateUserInfo()
        }
            .addOnFailureListener{
                Toast.makeText(requireContext(),"Kayıt başarısız oldu.",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        val timestamp=System.currentTimeMillis()
        val userId=firebaseAuth.uid
        val hashMap:HashMap<String,Any?> =HashMap()
        hashMap["uid"] = userId
        hashMap["firstName"] = firstName
        hashMap["lastName"] = lastName
        hashMap["email"] = email
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(userId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Lütfen tekrar giriş yapınız.",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }
            .addOnFailureListener{ e ->
                Toast.makeText(requireContext(),"Lütfen bilgilerinizi kontrol ediniz ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }
}