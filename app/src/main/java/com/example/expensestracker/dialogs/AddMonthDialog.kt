package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.expensestracker.databinding.AddMonthDialogBinding
import com.example.expensestracker.domain.models.Month
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddMonthDialog(
    context: Context,
    private val viewModel: MonthsViewModel
) :
    BottomSheetDialog(context) {
    private var _binding: AddMonthDialogBinding? = null
    private val binding: AddMonthDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = AddMonthDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureButton()

        return binding.root
    }

    private fun configureButton() = with(binding) {
        createMonthButton.setOnClickListener {
            val monthName = monthNameEditText.text.toString()
            if (monthName.isNotEmpty()) {
                val newMonth = Month(0, monthName, 0)
                viewModel.addMonth(newMonth, context)
                dismiss()
            } else {
                Toast.makeText(context, "Month name is required!", Toast.LENGTH_SHORT)
                    .show()
                monthNameEditText.requestFocus()
            }
        }
    }
}