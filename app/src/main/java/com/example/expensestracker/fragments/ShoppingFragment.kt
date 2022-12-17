package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensestracker.databinding.FragmentShoppingBinding
import com.example.expensestracker.fragments.delegates.viewBinding

class ShoppingFragment : Fragment() {

    private val binding by viewBinding(FragmentShoppingBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }
}