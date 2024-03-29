package com.erkankaraduman.fenogretmenm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.erkankaraduman.fenogretmenm.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

    }
    private fun initView() {
        btn_8class.setOnClickListener(this)
        btn_7class.setOnClickListener(this)
        btn_6class.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0) {
            btn_8class -> findNavController().navigate(R.id.action_homeFragment_to_contentsFragment)
        }

    }


}