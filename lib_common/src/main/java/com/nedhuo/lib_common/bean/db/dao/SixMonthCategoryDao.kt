package com.nedhuo.lib_common.bean.db.dao

import androidx.room.*
import com.nedhuo.lib_common.bean.db.mapping.SixMonthCategoryWithBook
import com.nedhuo.lib_common.bean.db.model.SixMonthCategoryModel

@Dao
interface SixMonthCategoryDao {
    /**
     * @Transaction 该查询会运行两次查询，保证原子执行
     */
    @Transaction
    @Query("select * from six_month_category")
    fun getCategoryWith(): List<SixMonthCategoryWithBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categoryModel: SixMonthCategoryModel?)

    @Query("select * from six_month_category where categoryName=:categoryName")
    fun getCategoryByName(categoryName: String): SixMonthCategoryModel?
}