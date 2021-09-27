package com.example.expense_tracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
// expenses are the monthly transactions
@Entity
data class Expense(
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name="amount")
    val amount:Float,
    @ColumnInfo(name="date")
    val date:String

) {
}