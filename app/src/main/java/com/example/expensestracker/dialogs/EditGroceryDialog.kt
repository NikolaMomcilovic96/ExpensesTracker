package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.expensestracker.databinding.EditGroceryDialogBinding
import com.example.expensestracker.viewmodel.GroceriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditGroceryDialog(
    context: Context,
    private val viewModel: GroceriesViewModel,
    private val groceryName: String,
    private val groceryId: Int
) : BottomSheetDialog(context) {
    private var _binding: EditGroceryDialogBinding? = null
    private val binding: EditGroceryDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = EditGroceryDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureSaveButton()
        configureDeleteButton()

        return binding.root
    }

    private fun configureSaveButton() = with(binding) {
        groceryNameEditText.setText(groceryName)

        saveChangesButton.setOnClickListener {
            if (groceryNameEditText.text.isNullOrEmpty()) {
                Toast.makeText(it.context, "Month name is required!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateGrocery(
                    groceryId,
                    groceryNameEditText.text.toString()
                )
                dismiss()
            }
        }
    }

    private fun configureDeleteButton() = with(binding) {
        deleteGroceryButton.setOnClickListener {
            viewModel.deleteGrocery(groceryId)
            dismiss()
        }
    }
}