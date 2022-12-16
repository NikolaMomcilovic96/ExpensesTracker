package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.AddExpenseDialogBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddExpenseDialog(
    context: Context,
    private val viewModel: MonthsViewModel,
    private val monthId: Int,
) : BottomSheetDialog(context) {
    private var _binding: AddExpenseDialogBinding? = null
    private val binding: AddExpenseDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = AddExpenseDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureButton()

        return binding.root
    }

    private fun configureButton() = with(binding) {
        addExpenseButton.setOnClickListener {
            val title = expenseTitleEditText.text.toString()
            val value = expenseValueEditText.text.toString()
            if (title.isEmpty() || value.isEmpty()) {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {
                val newExpense = Expense(0, monthId, title, value.toInt())
                viewModel.addExpense(newExpense)
                dismiss()
            }
        }
    }
}