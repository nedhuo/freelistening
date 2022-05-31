package com.nedhuo.module_listening.viewmodel

import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.ThreadUtils
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.nedhuo.lib_base.BaseViewModel
import com.nedhuo.lib_common.bean.db.AppDatabase
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.module_listening.JsoupManager

class SixMonthViewModel : BaseViewModel() {
    val dataUpdate = UnPeekLiveData<MutableList<SixMonthBookModel>>()

    /**
     * 加载解析网站数据
     */
    fun parseWebsite() {
        JsoupManager.mInstance.execute("http://m.6yueting.com")
    }

    /**
     * 获取书籍列表
     */
    fun getBookList() {
        ThreadUtils.executeByCached(object :
            ThreadUtils.SimpleTask<MutableList<SixMonthBookModel>?>() {
            override fun doInBackground(): MutableList<SixMonthBookModel>? {
                val allBooks = AppDatabase.mInstance.getSixMonthBookDao().getAllBooks()
                if (CollectionUtils.isEmpty(allBooks)) {
                    return null
                }
                return allBooks
            }

            override fun onSuccess(result: MutableList<SixMonthBookModel>?) {
                if (CollectionUtils.isEmpty(result)) {
                    parseWebsite()
                } else {
                    dataUpdate.value = result
                }
            }
        })
    }

    fun getBookDetail(bookSuffix: String) {

    }
}