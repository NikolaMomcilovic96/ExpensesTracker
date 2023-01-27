package com.example.expensestracker.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.expensestracker.databinding.AddGroceryDialogBinding
import com.example.expensestracker.domain.models.Grocery
import com.example.expensestracker.viewmodel.GroceriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddGroceryDialog(
    context: Context,
    private val viewModel: GroceriesViewModel
) : BottomSheetDialog(context) {
    private var _binding: AddGroceryDialogBinding? = null
    private val binding: AddGroceryDialogBinding
        get() = checkNotNull(_binding)

    fun showDialog() {
        setContentView(createView())
        show()
    }

    private fun createView(): View {
        _binding = AddGroceryDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        configureButton()

        return binding.root
    }

    private fun configureButton() = with(binding) {
        addGroceryButton.setOnClickListener {
            val groceryName = groceryNameEditText.text.toString()
            if (groceryName.isNotEmpty()) {
                val newGrocery = Grocery(0, groceryName, false)
                viewModel.addNewGrocery(newGrocery)
                dismiss()
            } else {
                Toast.makeText(context, "Grocery name is required!", Toast.LENGTH_SHORT)
                    .show()
                groceryNameEditText.requestFocus()
            }
        }
    }
}