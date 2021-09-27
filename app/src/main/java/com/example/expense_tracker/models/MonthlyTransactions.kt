package com.example.expense_tracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Relation
import com.example.expense_tracker.Transaction
// monthly transction consists of a list of expenses and other month details

@Entity
data class MonthlyTransactions(
        @ColumnInfo(name="monthYear")
        // to separate various months of different years from intermixing, a variable monthyear is taken which will be unique for each month of each year
        val monthYear:Long,
        @ColumnInfo(name="month")
        val month:Int,
        @ColumnInfo(name="year")
        val year:Int,
        @ColumnInfo(name="sum")
        val sum:Float,

        @Relation(parentColumn = "monthYear", entityColumn = "monthYear", entity = Transaction::class )
        val expenses :List<Expense>

        


)
