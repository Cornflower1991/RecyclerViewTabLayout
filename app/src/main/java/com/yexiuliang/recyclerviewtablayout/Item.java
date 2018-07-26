package com.yexiuliang.recyclerviewtablayout;

import java.util.List;

/**
 * Description: TODO
 *
 * @author yexiuliang on 2018/7/25
 */
public class Item {

    public String name;

    public List<SubItem> mSubItems;

    public static class SubItem {
        public String name;
        public String desc;

        public SubItem(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }
}
