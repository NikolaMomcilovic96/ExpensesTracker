package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.expensestracker.R
import com.example.expensestracker.databinding.FragmentBottomNavigationBinding
import com.example.expensestracker.fragments.delegates.viewBinding

class BottomNavigationFragment : Fragment() {

    private val binding by viewBinding(FragmentBottomNavigationBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        with(binding) {
            val nestedController =
                (childFragmentManager.findFragmentById(R.id.page_container) as NavHostFragment)
                    .navController
            bottomNavigation.setupWithNavController(nestedController)
        }

        return binding.root
    }
}