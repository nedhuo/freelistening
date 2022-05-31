package com.nedhuo.lib_common.bean.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * bookId               唯一键
 * bookName             书籍名称
 * bookImage            书籍图片
 * bookStatus           书籍状态（完结成度）
 * bookAuthor           书记作者
 * bookAnchor           书籍演播
 * bookCategory         书籍分类
 * bookSuffix           书籍链接后缀
 * bookSimpleDesc       书籍简介
 * bookDesc             书籍详情
 * bookEpisode          书籍总集数
 * bookRank             书籍排名
 * bookExtra            额外字段
 * */
@Entity(tableName = "six_month_book")

data class SixMonthBookModel @JvmOverloads constructor(
    @ColumnInfo(name = "bookId")
    @PrimaryKey(autoGenerate = true)
    var bId: Int? = null,

    @ColumnInfo(name = "bookName")
    var bookName: String? = null,

    @ColumnInfo(name = "bookImage")
    var bookImage: String? = null,

    @ColumnInfo(name = "bookStatus")
    var bookStatus: String? = null,

    @ColumnInfo(name = "bookAuthor")
    var bookAuthor: String? = null,

    @ColumnInfo(name = "bookAnchor")
    var bookAnchor: String? = null,

    @ColumnInfo(name = "bookCategory")
    var bookCategory: String? = null,

    @ColumnInfo(name = "bookSuffix")
    var bookSuffix: String? = null,

    @ColumnInfo(name = "bookSimpleDesc")
    var bookSimpleDesc: String? = null,

    @ColumnInfo(name = "bookDesc")
    var bookDesc: String? = null,

    @ColumnInfo(name = "bookEpisode")
    var bookEpisode: String? = null,

    @ColumnInfo(name = "bookRank")
    var bookRank: String? = null,

    @ColumnInfo(name = "bookExtra")
    var bookExtra: String? = null
)