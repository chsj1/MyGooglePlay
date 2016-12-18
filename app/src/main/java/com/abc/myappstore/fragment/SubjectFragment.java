package com.abc.myappstore.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.base.SuperBaseAdapter;
import com.abc.myappstore.bean.SubjectBean;
import com.abc.myappstore.factory.ListViewFactory;
import com.abc.myappstore.holder.SubjectHolder;
import com.abc.myappstore.protocol.SubjectProtocol;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 08:56
 * 描述	      ${TODO}
 *
 */
public class SubjectFragment extends BaseFragment {

    private SubjectProtocol   mProtocol;
    private List<SubjectBean> mDatas;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        mProtocol = new SubjectProtocol();
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
        listView.setAdapter(new SubjectAdapter(listView, mDatas));
        return listView;
    }

    class SubjectAdapter extends SuperBaseAdapter<SubjectBean> {

        public SubjectAdapter(AbsListView absListView, List<SubjectBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            return new SubjectHolder();
        }
    }
}
