package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import com.example.expensestracker.databinding.EditMonthDialogBinding
import com.example.expensestracker.viewmodel.MonthsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditMonthDialog(
    context: Context,
    private val viewModel: MonthsViewModel,
    private val monthName: String,
    private val monthId: Int,
    private val total: Int,
    private val monthDetailsNameTextView: TextView,
    private val navController: NavController
) : BottomSheetDialog(context) {
    private var _binding: EditMonthDialogBinding? = null
    private val binding: EditMonthDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = EditMonthDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureSaveButton()
        configureDeleteButton()

        return binding.root
    }

    private fun configureSaveButton() = with(binding) {
        monthNameEditText.setText(monthName)

        saveChangesButton.setOnClickListener {
            if (monthNameEditText.text.isNullOrEmpty()) {
                Toast.makeText(it.context, "Month name is required!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateMonth(
                    monthId,
                    monthNameEditText.text.toString(),
                    total
                )
                monthDetailsNameTextView.text = monthNameEditText.text.toString()
                dismiss()
            }
        }
    }

    private fun configureDeleteButton() = with(binding) {
        deleteMonthButton.setOnClickListener {
            viewModel.deleteMonth(monthId)
            dismiss()
            navController.popBackStack()
        }
    }
}