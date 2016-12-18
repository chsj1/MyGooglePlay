package com.abc.myappstore.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.utils.UIUtils;
import com.abc.myappstore.views.ChildViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 14:09
 * 描述	      ${TODO}
 *
 */
public class HomePictureHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {
    @InjectView(R.id.item_home_picture_pager)
    ChildViewPager mItemHomePicturePager;
    @InjectView(R.id.item_home_picture_container_indicator)
    LinearLayout   mItemHomePictureContainerIndicator;
    private List<String> mPictureUrls;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);
        //找孩子
        ButterKnife.inject(this, holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(List<String> pictureUrls) {
        //保存数据到成员变量
        mPictureUrls = pictureUrls;

        //绑定数据-->ViewPager
        mItemHomePicturePager.setAdapter(new HomePictureAdater());

        //绑定数据-->mItemHomePictureContainerIndicator
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //默认选中第一个
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }

            /*int width = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                    UIUtils.getResources().getDisplayMetrics()) + .5f)*/
            int width = UIUtils.dip2Px(6);
            int height = UIUtils.dip2Px(6);
            LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams(width, height);
            parmas.leftMargin = UIUtils.dip2Px(6);
            parmas.bottomMargin = UIUtils.dip2Px(6);
            mItemHomePictureContainerIndicator.addView(ivIndicator, parmas);
        }
        //监听ViewPager的切换
        mItemHomePicturePager.setOnPageChangeListener(this);


        //无限轮播
        int index = Integer.MAX_VALUE / 2;
        int diff = Integer.MAX_VALUE / 2 % mPictureUrls.size();
        index = index - diff;
        mItemHomePicturePager.setCurrentItem(index);

        //自动轮播
        final AutoScrollTask autoScrollTask  = new AutoScrollTask();
        autoScrollTask.start();

        //按下去的时候停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        autoScrollTask.start();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

    class AutoScrollTask implements Runnable {
        /**
         * 开始轮播
         */
        public void start() {
            UIUtils.getMainThreadHanlder().removeCallbacks(this);
            UIUtils.getMainThreadHanlder().postDelayed(this, 3000);
        }

        /**
         * 停止轮播
         */
        public void stop() {
            UIUtils.getMainThreadHanlder().removeCallbacks(this);
        }

        @Override
        public void run() {
            //希望滚动到下一页
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;

            mItemHomePicturePager.setCurrentItem(currentItem);

            start();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mPictureUrls.size();

        //修改indicator的选中效果
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
            //1.还原成默认的效果
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //2.选中为应该选中的效果
            if (position == i) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class HomePictureAdater extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPictureUrls != null) {
                //                return mPictureUrls.size();
                return Integer.MAX_VALUE;
//                return 10000;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % mPictureUrls.size();//0

            //view
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            //data
            String url = Constants.URLS.IMGBASEURL + mPictureUrls.get(position);

            //data+View
            ImageLoader.getInstance().displayImage(url, iv);
            //加入容器
            container.addView(iv);
            //返回页面
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
