# RecyclerViewTabLayout

![image](https://github.com/Cornflower1991/RecyclerViewTabLayout/blob/master/img/video.gif)


- 页面布局，采用CoordinatorLayout嵌套Recyclerview,TabLayout吸顶。和支付宝页面类似
- 内容区域采取嵌套Recyclerview的方式，复用同一个ViewPool




##### 仿支付宝“全部应用”模块，联动布局
1. 解决scrollToPositionWithOffset没有滚动动画的问题
2. 解决偶现点击TabLayout RecyclerView不滚动的问题
3. RecyclerView滑动过程TabLayout平滑滚动动画 
4. 动态计算最后一个item空白高度

#### TabLayout平滑滚动动画
```
 LinearLayoutManager layoutManager =(LinearLayoutManager)recyclerView.getLayoutManager();
 Tab tabAt = mTabLayout.getTabAt(layoutManager.findFirstVisibleItemPosition());
 if (tabAt != null && !tabAt.isSelected()) {
    tabAt.select();
 }
```
TabLayout点击item是有动画， mTabLayout.setScrollPosition() 这个方法没有动画。

#### scrollToPositionWithOffset没有滚动动画的问题
//平滑移动但是，只要那一项现在看得到了，那它就罢工了
 mRecyclerView.smoothScrollToPosition(position);
//直接requestLayout，没有动画
 mLinearLayoutManager.scrollToPositionWithOffset(position, 0);

解决办法 使用 SmoothScroller 移动到指定位置
1. 创建SmoothScroller
RecyclerView.SmoothScroller mSmoothScroller = new LinearSmoothScroller(context) {
  @Override protected int getVerticalSnapPreference() {
    return LinearSmoothScroller.SNAP_TO_START;
  }
};
2. 移动到想要的位置
mSmoothScroller.setTargetPosition(position);
mLinearLayoutManager.startSmoothScroll(mSmoothScroller);

#### 动态计算最后一个空白item高度
```
     @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_item, parent, false);
                return new ItemViewHolder(view);
            } else {
                //Footer是最后留白的位置，以便最后一个item能够出发tab的切换
                View view = new View(parent.getContext());
                Log.e("footer", "parentHeight: " + mRecyclerViewHeight + "--" + "itemHeight: " + itemHeight);
                view.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                mRecyclerViewHeight - itemHeight));
                return new FooterViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.mTitle.setText(mData.get(position).name);
                RecyclerView recyclerView = itemViewHolder.mRecyclerView;
                recyclerView.setRecycledViewPool(mRecycledViewPool);
                recyclerView.setHasFixedSize(false);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }

                    @Override
                    public void onLayoutCompleted(State state) {
                        super.onLayoutCompleted(state);
                        // 由于内部采取的Recyclerview嵌套的方式。直接post获取高度不准确。 
                        mRecyclerViewHeight = mRecyclerView.getHeight();
                        itemHeight = itemViewHolder.itemView.getHeight();
                        Log.e("onLayoutCompleted",
                                "itemHeight: " + itemHeight + "--" + position);
                    }
                });
                DemoAdapter demoAdapter = new DemoAdapter(mData.get(position).mSubItems);
                recyclerView.setAdapter(demoAdapter);

            }
        }
```

#### 解决偶现点击TabLayout RecyclerView不滚动的问题
加入FixAppBarLayoutBehavior解决问题


