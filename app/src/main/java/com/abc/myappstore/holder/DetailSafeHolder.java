package com.abc.myappstore.holder;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 15:09
 * 描述	      ${TODO}
 *
 */
public class DetailSafeHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {


    @InjectView(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;

    @InjectView(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;

    @InjectView(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;

    private boolean isOpen = true;
    private int mMeasuredHeight;

    @Override
    public View initHolderViewAndFindViews() {
        View hodlerView = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
        //找孩子
        ButterKnife.inject(this, hodlerView);

        //设置点击事件
        hodlerView.setOnClickListener(this);
        return hodlerView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        List<ItemInfoBean.ItemInfoSafeBean> itemInfoSafeBeanList = data.safe;
        for (ItemInfoBean.ItemInfoSafeBean itemInfoSafeBean : itemInfoSafeBeanList) {
            String safeDes = itemInfoSafeBean.safeDes;
            int safeDesColor = itemInfoSafeBean.safeDesColor;
            String safeDesUrl = itemInfoSafeBean.safeDesUrl;
            String safeUrl = itemInfoSafeBean.safeUrl;

            //mAppDetailSafePicContainer-->添加内容
            ImageView ivIcon = new ImageView(UIUtils.getContext());
            //图片的加载
            ImageLoader.getInstance().displayImage(Constants.URLS.IMGBASEURL + safeUrl, ivIcon);
            mAppDetailSafePicContainer.addView(ivIcon);


            //mAppDetailSafeDesContainer-->添加内容
            LinearLayout line = new LinearLayout(UIUtils.getContext());
            line.setGravity(Gravity.CENTER_VERTICAL);
            int padding = UIUtils.dip2Px(4);
            line.setPadding(padding, padding, padding, padding);

            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            TextView tvDes = new TextView(UIUtils.getContext());
            tvDes.setTextSize(UIUtils.sp2px(6));
            tvDes.setSingleLine(true);

            //赋值
            ImageLoader.getInstance().displayImage(Constants.URLS.IMGBASEURL + safeDesUrl, ivDesIcon);
            tvDes.setText(safeDes);
            if (safeDesColor == 0) {//正常色
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {//警告色
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }

            line.addView(ivDesIcon);
            line.addView(tvDes);

            mAppDetailSafeDesContainer.addView(line);
        }

        mAppDetailSafeDesContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMeasuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();
                LogUtils.s("mMeasuredHeight:" + mMeasuredHeight);
                mAppDetailSafeDesContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //一进来就默认修改DetailSafeDesContainer的Height
                //                changeDetailSafeDesContainerHeight(false);
            }
        });


    }

    @Override
    public void onClick(View v) {
        changeDetailSafeDesContainerHeight(true);
    }

    /**
     * 修改DetailSafeDesContainer的高度
     *
     * @param isAnimation 是否带动画效果
     */
    private void changeDetailSafeDesContainerHeight(boolean isAnimation) {
        if (isOpen) {
            //折叠 mAppDetailSafeDesContainer高度 从 应有的高度-->0
            //应有的高度
            int start = mMeasuredHeight;
            int end = 0;
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                //直接修改
                //通过layoutParams给mAppDetailSafeDesContainer设置具体的变化的高度
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = end;

                //重新赋值
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }

        } else {
            //展开  mAppDetailSafeDesContainer高度 从 0-->应有的高度
            int start = 0;
            int end = mMeasuredHeight;
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                //直接修改
                //通过layoutParams给mAppDetailSafeDesContainer设置具体的变化的高度
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = end;

                //重新赋值
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }
        }

        //状态取反
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.start();

        //监听得到渐变值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();

                //通过layoutParams给mAppDetailSafeDesContainer设置具体的变化的高度
                ViewGroup.LayoutParams curLayoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                curLayoutParams.height = height;

                //重新赋值
                mAppDetailSafeDesContainer.setLayoutParams(curLayoutParams);
            }
        });
        //箭头跟着旋转
        if (isOpen) {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180).start();
        }
    }
}
