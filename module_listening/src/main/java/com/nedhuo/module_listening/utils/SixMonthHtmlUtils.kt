package com.nedhuo.module_listening.utils

import com.nedhuo.lib_common.bean.db.AppDatabase
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.lib_common.bean.db.model.SixMonthCategoryModel
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * 由于链接原因，无法使用url去分类解析
 */
object SixMonthHtmlUtils {

    fun parseHtmlByType(type: SixMonthParseType, document: Document) {
        when (type) {
            SixMonthParseType.TYPE_HOME -> parseHome(document)

            SixMonthParseType.TYPE_LIST -> {

            }
        }
    }

    /**
     * 解析首页
     */
    private fun parseHome(document: Document) {
        parseHomeCategory(document)
        parseHomeNewestList(document)
        parseHomeRankList(document)
        parseHomeRecommendList(document)
    }

    /**
     * 首页分类解析
     * */
    private fun parseHomeCategory(document: Document) {
        val navWrapper = document.select(".nav-wrapper")
        navWrapper.forEach { element ->
            val categoryElements: Elements? = element.select(".nav-item")
            categoryElements?.let {
                it.forEach { element ->
                    val dataType = element.attr("data-type").toString()
                    val categorySuffix = element.select("a").attr("href").toString()
                    val categoryName = element.select("a").text()
                    val sixMonthCategoryDao = AppDatabase.mInstance.getSixMonthCategoryDao()
                    var categoryModel = sixMonthCategoryDao.getCategoryByName(categoryName)
                    categoryModel = categoryModel ?: SixMonthCategoryModel()
                    categoryModel.categoryName = categoryName
                    categoryModel.categorySuffix = categorySuffix
                    categoryModel.categoryName = categoryName
                    categoryModel.dateType = dataType
                    sixMonthCategoryDao.insertCategory(categoryModel)
                }
            }
        }
    }


    /**
     * 解析首页最新列表
     */
    private fun parseHomeNewestList(document: Document) {
        val newestElements = document.select(".newest-list-wrapper")
            .select(".newest-list").select(".clearfix")
        newestElements.forEach { element ->
            val itemSuffix = element.select("a").attr("href")
            val bookName = element.select("span.book-name").text()
            val bookStatus = element.select("span.book-status").text()
            var bookModel = AppDatabase.mInstance.getSixMonthBookDao().getBookByName(bookName)
            bookModel = bookModel ?: SixMonthBookModel()
            bookModel.bookName = bookName
            bookModel.bookSuffix = itemSuffix
            bookModel.bookStatus = bookStatus
            AppDatabase.mInstance.getSixMonthBookDao().insertBook(bookModel)
        }
    }


    /**
     * 解析首页排行列表
     */
    private fun parseHomeRankList(document: Document) {
        val rankElements = document.select(".rank-list-wrapper")
            .select(".rank-list").select(".clearfix")
        rankElements.forEach { element ->
            val itemSuffix = element.select("a").attr("href")
            val bookRank = element.select("span.rank-num").text()
            val bookName = element.select("span.book-name").text()
            val bookStatus = element.select(".book-status").text()
            var bookModel = AppDatabase.mInstance.getSixMonthBookDao().getBookByName(bookName)
            bookModel = bookModel ?: SixMonthBookModel()
            bookModel.bookName = bookName
            bookModel.bookSuffix = itemSuffix
            bookModel.bookStatus = bookStatus
            bookModel.bookRank = bookRank
            AppDatabase.mInstance.getSixMonthBookDao().insertBook(bookModel)
        }
    }

    /**
     * 首页列表数据解析
     */
    private fun parseHomeRecommendList(document: Document) {
        //推荐
        val recommendElements = document.select(".list-wrapper")
            .select(".recommand-book").select(".album-list")
            .select(".album-item")
        recommendElements.forEach { element ->
            val bookImg = element.select(".book-item-img")
                .select("img").attr("src").toString()
            val bookInfoElement = element.select(".book-item-r")
            val bookUrlSuffix = bookInfoElement.select(".book-item-name")
                .select("a").attr("href").toString()
            val bookName = bookInfoElement.select(".book-item-name")
                .select("a").text()
            val bookStatus = bookInfoElement.select(".book-item-status").text()
            val bookAuthor = bookInfoElement.select(".book-item-info").select("span.g-user").text()
            val bookDesc = bookInfoElement.select(".book-item-desc").text()

            var bookModel = AppDatabase.mInstance.getSixMonthBookDao().getBookByName(bookName)
            bookModel = bookModel ?: SixMonthBookModel()
            bookModel.bookName = bookName
            bookModel.bookImage = bookImg
            bookModel.bookAuthor = bookAuthor
            bookModel.bookStatus = bookStatus
            bookModel.bookDesc = bookDesc
            AppDatabase.mInstance.getSixMonthBookDao().insertBook(bookModel)
        }
    }

    /**
     * 列表Item解析
     */
    fun parseItem(document: Document) {
        val bookInfoElements = document.select(".content-wrapper").select(".content-l")
        //播放信息
        val itemTopElements = bookInfoElements.select(".book-item-top")
        val bookImage = itemTopElements.select(".book-item-img")
            .select("img[src]").text()
        val bookItemRElements = itemTopElements.select(".book-item-r")
        val bookName = bookItemRElements.select(".book-item-name").text()
        val bookStatus = bookItemRElements.select(".book-item-name")
            .select(".status-serial").text()
        val bookAuthor = bookItemRElements.select(".book-item-info").select("a.author").text()
        //主播
        val bookAnchor = bookItemRElements.select(".book-item-info").select("a.g-user").text()
        val bookCategory = bookItemRElements.select("span.book-type").text()
        val bookUpdateTime = bookItemRElements.select("div:eq(2)").select("span:eq(1)").text()
        //部分介绍
        val bookDesc = bookItemRElements.select(".book-item-desc").text()
        val bookDescSuffix = bookItemRElements.select(".book-item-desc").select("a").attr("href")
        val bookSuffix = bookItemRElements.select("a.listen-btn").attr("href")

        //播放列表
        val playListElements = bookInfoElements.select(".play-list")
            .select(".list").select("li")
        playListElements.forEach { element ->
            val playSuffix = element.select("a[href]")
            val playEpisode = element.select("a").text()
        }

        //列表分页
        val pageElements = bookInfoElements.select(".pagination").select("a")
        pageElements.forEach { element ->
            val firstPageSuffix = element.select(".first").attr("href")
            val nextPageSuffix = element.select(".next").attr("href")
            val lastPageSuffix = element.select(".last").attr("href")
        }
        //全部介绍
        val bookDetail = bookInfoElements.select(".detail-intro").select(".detail-text").text()

        var bookModel = AppDatabase.mInstance.getSixMonthBookDao().getBookByName(bookName)
        bookModel = bookModel ?: SixMonthBookModel()
        bookModel.bookName = bookName
        bookModel.bookImage = bookImage
        bookModel.bookAuthor = bookAuthor
        bookModel.bookStatus = bookStatus
        bookModel.bookAnchor = bookAnchor
        bookModel.bookCategory = bookCategory
        bookModel.bookSimpleDesc = bookDesc
        bookModel.bookDesc = bookDetail
        bookModel.bookSuffix = bookSuffix
        AppDatabase.mInstance.getSixMonthBookDao().insertBook(bookModel)
    }


    enum class SixMonthParseType {
        TYPE_HOME, TYPE_LIST,
    }
}