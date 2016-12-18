package com.abc.myappstore.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.protocol.RecommendProtocol;
import com.abc.myappstore.utils.UIUtils;
import com.abc.myappstore.views.flyinout.ShakeListener;
import com.abc.myappstore.views.flyinout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/6 08:56
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-11 10:59:18 +0800 (星期一, 11 七月 2016) $
 * 更新描述   ${TODO}
 */
public class RecommendFragment extends BaseFragment {

    private List<String>  mDatas;
    private ShakeListener mShakeListener;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        RecommendProtocol protocol = new RecommendProtocol();
        try {
            mDatas = protocol.loadData(0);
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
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        //设置数据
        final RecommendAdapter adapter = new RecommendAdapter();
        stellarMap.setAdapter(adapter);

        //第一页没有显示
        stellarMap.setGroup(0, true);

        //拆分屏幕
        stellarMap.setRegularity(15, 20);


        //摇一摇切换
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int currentGroup = stellarMap.getCurrentGroup();
                if (currentGroup == adapter.getGroupCount() - 1) {//最后一页
                    currentGroup = 0;
                } else {
                    currentGroup++;
                }
                //设置
                stellarMap.setGroup(currentGroup, true);
            }
        });

        return stellarMap;
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener.pause();
        }
        super.onPause();
    }

    class RecommendAdapter implements StellarMap.Adapter {
        //每组显示多少个
        public static final int PAGESIZE = 15;

        @Override
        public int getGroupCount() {//得到一共多少组
            int count = mDatas.size() / PAGESIZE;// 32/15=2
            if (mDatas.size() % PAGESIZE == 0) {
                return count;
            } else {
                return count + 1;
            }
        }

        @Override
        public int getCount(int group) {//每组多少个
            if (mDatas.size() % PAGESIZE == 0) {//15 15 2

                return PAGESIZE;
            } else {
                if (group == getGroupCount() - 1) {//最后一页
                    //显示余数
                    return mDatas.size() % PAGESIZE;
                } else {
                    return PAGESIZE;
                }
            }
        }

        @Override
        public View getView(int group, int position, View convertView) {//具体的View
            int index = group * PAGESIZE + position;
            //data
            String data = mDatas.get(index);
            //view
            TextView tv = new TextView(UIUtils.getContext());
            //3.随机大小和随机颜色
            Random random = new Random();
            tv.setTextSize(14 + random.nextInt(4));//14-18

            int alpha = 255;
            int red = 50 + random.nextInt(150);//50-200
            int green = 50 + random.nextInt(150);//50-200
            int blue = 50 + random.nextInt(150);//50-200
            tv.setTextColor(Color.argb(alpha, red, green, blue));

            //data+view
            tv.setText(data);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}