package com.nedhuo.lib_common.bean.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liulishuo.okdownload.OkDownloadProvider.context
import com.nedhuo.lib_common.bean.db.dao.SixMonthBookDao
import com.nedhuo.lib_common.bean.db.dao.SixMonthCategoryDao
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.lib_common.bean.db.model.SixMonthCategoryModel

/**
 * https://developer.android.google.cn/training/data-storage/room?hl=zh-cn
 * 该类必须带有 @Database 注解，该注解包含列出所有与数据库关联的数据实体的 entities 数组。
 * 该类必须是一个抽象类，用于扩展 RoomDatabase。
 * 对于与数据库关联的每个 DAO 类，数据库类必须定义一个具有零参数的抽象方法，并返回 DAO 类的实例。
 */
@Database(
    entities = [SixMonthBookModel::class, SixMonthCategoryModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val mInstance: AppDatabase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
            ).build()
        }

    }

    abstract fun getSixMonthBookDao(): SixMonthBookDao

    abstract fun getSixMonthCategoryDao(): SixMonthCategoryDao
}