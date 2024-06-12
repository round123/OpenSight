package com.tao.opensight.ui

import android.os.Bundle
import com.tao.opensight.R
import com.tao.opensight.databinding.LayoutMainBinding
import com.tao.opensight.ext.setOnClickListener
import com.tao.opensight.ui.common.BaseActivity

class MainActivity : BaseActivity() {
    private var _binding: LayoutMainBinding? = null
    private val binding: LayoutMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

     private fun setupViews() {

    }


}