package com.abc.myappstore.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.protocol.HotProtocol;
import com.abc.myappstore.utils.UIUtils;
import com.abc.myappstore.views.flyinout.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 08:56
 * 描述	      ${TODO}
 *
 */
public class HotFragment extends BaseFragment {

    private List<String> mDatas;

    /**
     * @return
     * @des 真正在子线程中开始加载具体的数据
     * @called 外界需要加载数据的时候, 如果调用了triggerLoadData()方法的时候
     */
    @Override
    public LoadingPager.LoadedResult initData() {
        HotProtocol protocol = new HotProtocol();
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
        ScrollView sv = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());


        for (String data : mDatas) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);

            tv.setTextColor(Color.WHITE);
            int padding = UIUtils.dip2Px(4);
            tv.setPadding(padding, padding, padding, padding);
            tv.setGravity(Gravity.CENTER);

            //设置圆角背景
            //            tv.setBackgroundResource(R.drawable.bg_hot_tv);
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setCornerRadius(UIUtils.dip2Px(8));

            Random random = new Random();
            int alpha = 255;
            int red = 50 + random.nextInt(150);//50-200
            int green = 50 + random.nextInt(150);//50-200
            int blue = 50 + random.nextInt(150);//50-200

            //自行调色
            int argb = Color.argb(alpha, red, green, blue);
            normalBg.setColor(argb);

            //创建一个按下去的图片
            GradientDrawable pressedBg = new GradientDrawable();
            pressedBg.setColor(Color.DKGRAY);
            pressedBg.setCornerRadius(UIUtils.dip2Px(8));


            //带有状态的背景
            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{-android.R.attr.state_pressed}, normalBg);
            selectorBg.addState(new int[]{android.R.attr.state_pressed}, pressedBg);
            tv.setBackgroundDrawable(selectorBg);

            //设置tv可点击
            tv.setClickable(true);

            flowLayout.addView(tv);
        }

        sv.addView(flowLayout);
        return sv;
    }
}
