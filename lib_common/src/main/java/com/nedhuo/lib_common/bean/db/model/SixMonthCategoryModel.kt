package com.nedhuo.lib_common.bean.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * cid                  唯一键
 * bookName             书籍名称
 * bookImage            书籍图片
 *
 * 书籍
 */
@Entity(tableName = "six_month_category")
data class SixMonthCategoryModel @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    var cid: Int = 0,

    @ColumnInfo(name = "categorySuffix")
    var categorySuffix: String? = null,

    //与 six_month_book 表建立一对多的关系
    @ColumnInfo(name = "categoryName")
    var categoryName: String? = null,

    @ColumnInfo(name = "dateType")
    var dateType: String? = null
)