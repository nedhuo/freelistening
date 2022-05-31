package com.nedhuo.module_listening

import android.os.Build
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.nedhuo.module_listening.net.SIX_MONTH_HOME_URL
import com.nedhuo.module_listening.utils.SixMonthHtmlUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Jsoup任务管理Manager
 * */
class JsoupManager private constructor() {
    private var mTaskMap: HashMap<String, JsoupTask> = hashMapOf()
    var mUrlTypeMap: HashMap<String, SixMonthHtmlUtils.SixMonthParseType> = hashMapOf()

    //支持的解析链接
    private var mWebsiteList: ArrayList<String> = arrayListOf()
    private var jsoupTask: JsoupTask? = null

    companion object {
        val mInstance: JsoupManager by lazy { JsoupManager() }
    }

    init {
        //添加相关网站
        mWebsiteList.add(SIX_MONTH_HOME_URL)
    }


    /**
     * 执行网址解析
     * @param url 解析网址链接
     * @param type 解析网站类型（确认哪个网站 后期可以根据链接去判断 目前不确定链接都哪些）
     */
    fun execute(url: String, type: SixMonthHtmlUtils.SixMonthParseType) {
        //url校验
        if (checkUrl(url)) {
            ToastUtils.showShort("访问url不符合规则")
            return
        }
        //url type保存
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mUrlTypeMap.putIfAbsent(url, type)
        } else {
            mUrlTypeMap[url] = mUrlTypeMap[url] ?: type
        }

        //任务缓存中是否存在该任务
        if (mTaskMap[url] != null && mTaskMap[url] is JsoupTask) {
            jsoupTask = mTaskMap[url]!!
        } else {
            jsoupTask = JsoupTask(url)
            mTaskMap[url] = jsoupTask!!
        }

        ThreadUtils.executeByCached(jsoupTask)
    }


    /**
     * 线程加载解析任务
     */
    inner class JsoupTask(private val url: String) : ThreadUtils.SimpleTask<String>() {
        override fun doInBackground(): String? {
            var document: Document?
            try {
                document = Jsoup.connect(url).get()
            } catch (e: Exception) {
                return null
            }
            SixMonthHtmlUtils.parseHtmlByType(
                mUrlTypeMap[url]!!,
                document
            )
            return url
        }

        override fun onSuccess(result: String?) {
            result?.run {
                // GlobalScope是什么 为什么emit在suspend中调用
                GlobalScope.launch {
                    //更新相关类型的解析监听
                    parseUpdateFlow.emit(mUrlTypeMap[url]!!)
                }
            }


        }
    }


    /**
     * 字符串校验 是否符合
     * @return  true  校验结果异常
     *          false 校验结果正常
     */
    private fun checkUrl(url: String): Boolean {
        if (StringUtils.isEmpty(url)) {
            return true
        }
        mWebsiteList.forEach {
            if (it.contains(url)) {
                return false
            }
        }
        return true
    }


}