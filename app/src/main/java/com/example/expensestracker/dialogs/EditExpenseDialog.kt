package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.databinding.ExpenseDetailsDialogBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditExpenseDialog(
    context: Context,
    private val viewModel: MonthsViewModel,
    private val expense: Expense
) : BottomSheetDialog(context) {
    private var _binding: ExpenseDetailsDialogBinding? = null
    private val binding: ExpenseDetailsDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = ExpenseDetailsDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureSaveButton()
        configureDeleteButton()

        return binding.root
    }

    private fun configureSaveButton() = with(binding) {
        expenseNameDetailEditText.setText(expense.title)
        expenseValueDetailEditText.setText(expense.price.toString())

        saveButton.setOnClickListener {
            val title = expenseNameDetailEditText.text.toString()
            val value = expenseValueDetailEditText.text.toString()
            val extra = expenseExtraEditText.text.toString()
            if (title.isNotEmpty() && value.isNotEmpty()) {
                if (extra.isEmpty()) {
                    val newExpense = Expense(
                        expense.id,
                        expense.monthId,
                        title,
                        value.toInt()
                    )
                    viewModel.updateExpense(newExpense)
                } else {
                    val total = value.toInt() + extra.toInt()
                    val newExpense = Expense(expense.id, expense.monthId, title, total)
                    viewModel.updateExpense(newExpense)
                }
                dismiss()
            } else {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureDeleteButton() = with(binding) {
        deleteButton.setOnClickListener {
            viewModel.deleteExpense(expense)
            dismiss()
        }
    }
}