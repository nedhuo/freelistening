package com.nedhuo.module_listening.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.module_listening.R
import com.nedhuo.module_listening.databinding.ListeningItemRvSixMonthBookBinding

class SixMonthBookAdapter :
    BaseQuickAdapter<SixMonthBookModel, BaseViewHolder>(R.layout.listening_item_rv_six_month_book) {
    override fun convert(holder: BaseViewHolder, item: SixMonthBookModel) {
        val binding = DataBindingUtil.bind<ListeningItemRvSixMonthBookBinding>(holder.itemView)

        binding?.run {
            tvName.text = item.bookName
            tvDesc.text = item.bookSimpleDesc
        }
    }
}