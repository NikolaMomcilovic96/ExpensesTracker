package com.example.expensestracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensestracker.adapters.ToDoViewPagerAdapter
import com.example.expensestracker.databinding.FragmentToDoBinding
import com.example.expensestracker.fragments.delegates.viewBinding

class ToDoFragment : Fragment() {

    private val binding by viewBinding(FragmentToDoBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupTabs()

        return binding.root
    }

    private fun setupTabs() = with(binding) {
        val adapter = ToDoViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ShoppingFragment(), "Shopping")
        adapter.addFragment(TasksFragment(), "Tasks")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)
    }
}