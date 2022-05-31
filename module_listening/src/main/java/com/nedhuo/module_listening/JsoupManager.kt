package com.nedhuo.module_listening

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ThreadUtils
import com.nedhuo.module_listening.net.SIX_MONTH_HOME_URL
import com.nedhuo.module_listening.utils.SixMonthHtmlUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Jsoup任务管理Manager
 * */
class JsoupManager private constructor() {
    private var mTaskMap: HashMap<String, JsoupTask> = hashMapOf()

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
    fun execute(url: String) {
        if (checkUrl(url)) {
            return
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
    class JsoupTask(private val url: String) : ThreadUtils.SimpleTask<Boolean>() {
        override fun doInBackground(): Boolean {
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get()
            } catch (e: Exception) {
                return false
            }
            SixMonthHtmlUtils.parseHtmlByType(
                SixMonthHtmlUtils.SixMonthParseType.TYPE_HOME,
                document
            )
            return true
        }

        override fun onSuccess(result: Boolean?) {
            //todo 到此处 数据已经解析成功 保存到本地 刷新页面 通知时把Url发出去 移除任务
            GlobalScope.launch {
                parseUpdateFlow.emit(SixMonthHtmlUtils.SixMonthParseType.TYPE_HOME)
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