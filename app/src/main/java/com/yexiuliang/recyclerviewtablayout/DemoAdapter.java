package com.yexiuliang.recyclerviewtablayout;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yexiuliang.recyclerviewtablayout.Item.SubItem;

import java.util.List;

/**
 * Description: TODO
 *
 * @author yexiuliang on 2018/7/25
 */
public class DemoAdapter extends BaseQuickAdapter<SubItem, BaseViewHolder> {

    public DemoAdapter(@Nullable List<SubItem> data) {
        super(R.layout.demo_subitem,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, SubItem item) {
        holder.setText(R.id.demo_subitem_text,item.name);
        holder.setText(R.id.demo_subitem_desc,item.desc);
    }
}
