package com.nedhuo.lib_common.bean.db.mapping

import androidx.room.Embedded
import androidx.room.Relation
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.lib_common.bean.db.model.SixMonthCategoryModel

/**
 * 书类与书籍之间的映射表
 */
data class SixMonthCategoryWithBook(
    @Embedded val categoryModel: SixMonthCategoryModel,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "bookCategory"
    )
    val bookList: List<SixMonthBookModel>

)