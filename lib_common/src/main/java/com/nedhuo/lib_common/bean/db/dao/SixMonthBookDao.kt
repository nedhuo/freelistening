package com.nedhuo.lib_common.bean.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel

@Dao
interface SixMonthBookDao {
    @Query("select * from `six_month_book`")
    fun getAllBooks(): MutableList<SixMonthBookModel>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(bookModel: SixMonthBookModel?)

    @Query("delete from `six_month_book` where bookName=:bookName")
    fun deleteGroup(bookName: String?)

    @Query("select * from `six_month_book` where bookName=:bookName")
    fun getBookByName(bookName: String?): SixMonthBookModel?
}