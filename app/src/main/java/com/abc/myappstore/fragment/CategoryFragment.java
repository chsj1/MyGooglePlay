package com.abc.myappstore.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.base.SuperBaseAdapter;
import com.abc.myappstore.bean.CategoryInfoBean;
import com.abc.myappstore.factory.ListViewFactory;
import com.abc.myappstore.holder.CategoryNormalHolder;
import com.abc.myappstore.holder.CategoryTitleHolder;
import com.abc.myappstore.protocol.CategoryProtocol;
import com.abc.myappstore.utils.LogUtils;

import java.util.List;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 08:56
 * 描述	      ${TODO}
 *
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfoBean> mDatas;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        CategoryProtocol protocol = new CategoryProtocol();
        try {
            mDatas = protocol.loadData(0);
            LogUtils.printList(mDatas);
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
        listView.setAdapter(new CategoryAdapter(listView, mDatas));
        return listView;
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

        public CategoryAdapter(AbsListView absListView, List<CategoryInfoBean> dataSource) {
            super(absListView, dataSource);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            CategoryInfoBean categoryInfoBean = mDatas.get(position);
            if (categoryInfoBean.isTitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryNormalHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;//3
        }

       /* @Override
        public int getItemViewType(int position) {//0 1 2
            CategoryInfoBean categoryInfoBean = mDatas.get(position);
            if (position == getCount() - 1) {
                return VIEWTYPE_LOADMORE;
            } else {
                if (categoryInfoBean.isTitle) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }*/

        @Override
        public int getNorItemViewType(int position) {// 0 1 2
            CategoryInfoBean categoryInfoBean = mDatas.get(position);
            if (categoryInfoBean.isTitle) {
                return 2;
            } else {
                return 1;
            }
        }
    }
}
