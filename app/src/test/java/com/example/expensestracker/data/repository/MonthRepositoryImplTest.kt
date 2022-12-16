package com.example.expensestracker.data.repository

import com.example.expensestracker.data.database.MonthsDao
import com.example.expensestracker.data.models.Expense
import com.example.expensestracker.data.models.Month
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class MonthRepositoryImplTest {
    private lateinit var monthRepositoryImpl: MonthsRepositoryImpl
    private var monthsDaoMock: MonthsDao = mock()

    @Before
    fun setup() {
        monthRepositoryImpl = MonthsRepositoryImpl(monthsDaoMock)
    }

    @Test
    fun getAllMonths() = runBlocking {
        Mockito.`when`(monthsDaoMock.getMonths()).thenReturn(months)

        Assert.assertEquals(months, monthRepositoryImpl.getMonths())
    }

    @Test
    fun addNewMonth() = runBlocking {
        monthRepositoryImpl.addMonth(month)
        verify(monthsDaoMock, times(1)).addMonth(month)
    }

    @Test
    fun deleteMonth() = runBlocking {
        monthRepositoryImpl.deleteMonth(MONTH_ID)
        verify(monthsDaoMock, times(1)).deleteMonth(MONTH_ID)
    }

    @Test
    fun updateMonth() = runBlocking {
        monthRepositoryImpl.updateMonth(MONTH_ID, MONTH_UPDATED_NAME, MONTH_TOTAL)

        Assert.assertNotEquals(month, Month(MONTH_ID, MONTH_UPDATED_NAME, MONTH_TOTAL))
    }

    @Test
    fun getAllExpenses() = runBlocking {
        Mockito.`when`(monthsDaoMock.getExpenses(MONTH_ID)).thenReturn(expenses)

        Assert.assertEquals(expenses, monthRepositoryImpl.getExpenses(MONTH_ID))
    }

    @Test
    fun addNewExpense() = runBlocking {
        monthRepositoryImpl.addExpense(expense)
        verify(monthsDaoMock, times(1)).addExpense(expense)
    }

    @Test
    fun deleteExpense() = runBlocking {
        monthRepositoryImpl.deleteExpense(EXPENSE_ID)
        verify(monthsDaoMock, times(1)).deleteExpense(EXPENSE_ID)
    }

    @Test
    fun updateExpense() = runBlocking {
        monthRepositoryImpl.updateExpense(
            EXPENSE_ID,
            EXPENSE_UPDATED_TITLE,
            EXPENSE_ADDITIONAL_COSTS
        )
        Assert.assertNotEquals(
            expense,
            Expense(EXPENSE_ID, EXPENSE_MONTH_ID, EXPENSE_UPDATED_TITLE, EXPENSE_ADDITIONAL_COSTS)
        )
    }

    @Test
    fun deleteAllData() = runBlocking {
        monthRepositoryImpl.deleteAllData()
        verify(monthsDaoMock, times(1)).deleteAllMonths()
        verify(monthsDaoMock, times(1)).deleteAllExpenses()
    }

    companion object {

        private const val MONTH_ID = 0
        private const val MONTH_NAME = "MONTH_NAME"
        private const val MONTH_TOTAL = 10000
        private const val MONTH_UPDATED_NAME = "UPDATED_NAME"

        private const val EXPENSE_ID = 0
        private const val EXPENSE_MONTH_ID = 0
        private const val EXPENSE_TITLE = "EXPENSE_TITLE"
        private const val EXPENSE_PRICE = 1000
        private const val EXPENSE_UPDATED_TITLE = "UPDATED TITLE"
        private const val EXPENSE_ADDITIONAL_COSTS = 500

        private val month = Month(
            id = MONTH_ID,
            name = MONTH_NAME,
            total = MONTH_TOTAL
        )

        private val expense = Expense(
            id = EXPENSE_ID,
            monthId = EXPENSE_MONTH_ID,
            title = EXPENSE_TITLE,
            price = EXPENSE_PRICE
        )

        private val months = listOf(month)
        private val expenses = listOf(expense)
    }
}