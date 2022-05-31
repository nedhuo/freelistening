package com.nedhuo.module_listening

import com.nedhuo.module_listening.utils.SixMonthHtmlUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 数据解析完成更新Flow
 */
val parseUpdateFlow by lazy {
    MutableSharedFlow<SixMonthHtmlUtils.SixMonthParseType>()
}