package com.abc.myappstore.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.ItemAdapter;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.base.MyApplication;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.factory.ListViewFactory;
import com.abc.myappstore.holder.ItemHolder;
import com.abc.myappstore.manager.DownLoadInfo;
import com.abc.myappstore.manager.DownLoadManager;
import com.abc.myappstore.protocol.AppProtocol;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 08:56
 * 描述	      ${TODO}
 *
 */
public class AppFragment extends BaseFragment {

    private AppProtocol        mProtocol;
    private List<ItemInfoBean> mDatas;
    private AppAdapter mAdapter;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        mProtocol = new AppProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkResResult(mDatas);
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
        ListView listView = ListViewFactory.createListView();

        mAdapter = new AppAdapter(MyApplication.getContext(), mDatas);
        listView.setAdapter(mAdapter);
        return listView;
    }

    class AppAdapter extends ItemAdapter {

        public AppAdapter(Context context, List<ItemInfoBean> dataSource) {
            super(context, dataSource);
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
