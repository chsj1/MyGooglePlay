package com.abc.myappstore.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.ItemAdapter;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.bean.HomeBean;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.factory.ListViewFactory;
import com.abc.myappstore.holder.HomePictureHolder;
import com.abc.myappstore.holder.ItemHolder;
import com.abc.myappstore.manager.DownLoadInfo;
import com.abc.myappstore.manager.DownLoadManager;
import com.abc.myappstore.protocol.HomeProtocol;

import java.util.List;

/**
 * 创建者     chris
 * 创建时间   2016/7/6 08:56
 * 描述	      动态添加和移除观察者
 *
 */
public class HomeFragment extends BaseFragment {

    private List<String>       mDatas;
    private List<String>       mPictureUrls;
    private List<ItemInfoBean> mItemInfoBeanList;
    private HomeProtocol       mProtocol;
    private HomeBaseAdapter    mAdapter;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {

        /*--------------- 对HomeFragment里面的网络请求做了简单的封装以后 ---------------*/
        try {
            mProtocol = new HomeProtocol();
            HomeBean homeBean = mProtocol.loadData(0);

            LoadingPager.LoadedResult state = checkResResult(homeBean);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现问题,homeBean==null
                return state;
            }

            mItemInfoBeanList = homeBean.list;
            mPictureUrls = homeBean.picture;

            state = checkResResult(mItemInfoBeanList);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现了问题,mItemInfoBeanList.size==0
                return state;
            }

            state = checkResResult(mPictureUrls);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现了问题,mPictureUrls.size==0
                return state;
            }

            return state;//这个时候的state肯定就是success
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    /**
     * @return
     * @des 1.决定成功视图长什么样子-->xml
     * @des 2.数据和视图的绑定-->因为此时数据已经加载回来
     * @call 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候,数据加载完成,数据加载成功
     */
    @Override
    public View initSuccessView() {
        //view->successView-->ListView
        ListView listView = ListViewFactory.createListView();

        //dataSets-->网络请求回来的数据-->mDatas

        //给listview添加一个轮播图
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        listView.addHeaderView(homePictureHolder.mHolderView);//headerView-->是一个经过了数据绑定的View(data+view)
        homePictureHolder.setDataAndRefreshHolderView(mPictureUrls);

        mItemInfoBeanList.remove(0);// 不要第一个数据

        //data+view
        mAdapter = new HomeBaseAdapter(listView, mItemInfoBeanList);
        listView.setAdapter(mAdapter);

        return listView;
    }

    class HomeBaseAdapter extends ItemAdapter {
        public HomeBaseAdapter(AbsListView absListView, List<ItemInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        /**
         * @return 2.具体实现加载更多
         * @des 真正的在子线程中加载更多
         */
        @Override
        public List<ItemInfoBean> onLoadMore() throws Exception {
            SystemClock.sleep(3000);
            HomeBean homeBean = mProtocol.loadData(mItemInfoBeanList.size());
            if (homeBean != null) {
                List<ItemInfoBean> loadmoreList = homeBean.list;
                return loadmoreList;
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        //添加观察者
        //adapter-->所有的观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().addObserver(itemHolder);

                //手动发布最新的DownLoadInfo的状态
                DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(itemHolder.mData);
                DownLoadManager.getInstance().notifyObservers(downLoadInfo);
            }

        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //移除观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().deleteObserver(itemHolder);
            }
        }
        super.onPause();
    }
}
