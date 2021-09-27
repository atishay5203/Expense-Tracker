package com.example.expense_tracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year
enum class trans
{
    Upcoming ,Missed
}
enum class TransactionType
{
    Cash,  Credit, Debit
}
enum class SortType
{
    All, Cash, Credit, Debit
}
enum class TransactionCategory
{
    Education,Travel,Food, Accessories, Other
}
// this is the main entity , transaction
@Entity(tableName="transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)val id:Long,
    val name:String,
    val amount:Float,
    val description:String,
    val type:Int,
    val category:String,
    val increment:Int,
    val isCompleted:Int,
    val recurring:Int,

    @ColumnInfo(name = "date")
    val date: String,
    val fromDate: String,
    val toDate: String,
    val monthYear: Long,
    val month: Int,
    val year: Int


    )
