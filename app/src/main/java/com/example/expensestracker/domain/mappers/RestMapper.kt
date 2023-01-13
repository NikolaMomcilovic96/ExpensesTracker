package com.example.expensestracker.domain.mappers

interface RestMapper<MONTH, EXPENSES, OUT> {
    fun map(month: MONTH, expenses: EXPENSES): OUT
}