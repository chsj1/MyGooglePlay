package com.abc.myappstore.holder;

import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 15:09
 * 描述	      ${TODO}
 *
 */
public class DetailDesHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {


    @InjectView(R.id.app_detail_des_tv_des)
    TextView  mAppDetailDesTvDes;
    @InjectView(R.id.app_detail_des_tv_author)
    TextView  mAppDetailDesTvAuthor;
    @InjectView(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;

    private boolean isOpen = true;
    private int          mAppDetailDesTvDesMeasuredHeight;
    private ItemInfoBean mItemInfoBean;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        ButterKnife.inject(this, holderView);

        holderView.setOnClickListener(this);
        //找孩子
        return holderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        //保存数据为成员变量
        mItemInfoBean = data;


        mAppDetailDesTvAuthor.setText(data.author);
        mAppDetailDesTvDes.setText(data.des);

        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppDetailDesTvDesMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //默认折叠
                changeDetailDesHeight(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        changeDetailDesHeight(true);
    }

    private void changeDetailDesHeight(boolean isAnimation) {
        if (isOpen) {
            //折叠 mAppDetailDesTvDes高度 从  应有的高度-->7行的高度
            //            应有的高度

            Toast.makeText(UIUtils.getContext(), "" + mAppDetailDesTvDesMeasuredHeight, Toast.LENGTH_SHORT).show();

            int start = mAppDetailDesTvDesMeasuredHeight;
            int end = getShortMeasureHeight(7, mItemInfoBean.des);//计算7行高度
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mAppDetailDesTvDes.setHeight(end);
            }
        } else {
            //展开

            int start = getShortMeasureHeight(7, mItemInfoBean.des);//计算7行高度
            int end = mAppDetailDesTvDesMeasuredHeight;

            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mAppDetailDesTvDes.setHeight(end);
            }
        }
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        animator.start();

        //监听动画执行完成
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //找到外层的ScrollView进行滚动效果
                ViewParent parent = mAppDetailDesTvDes.getParent();

                while (true) {
                    parent = parent.getParent();

                    if (parent instanceof ScrollView) {
                        //开始滚动
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }

                    if (parent == null) {//最终就没有找到
                        break;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        if (isOpen) {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180).start();
        }
    }

    private int getShortMeasureHeight(int lineNum, String content) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setLines(lineNum);//设置了行高
        tempTextView.setText(content);


        tempTextView.measure(0, 0);

        int measuredHeight = tempTextView.getMeasuredHeight();
        return measuredHeight;
    }
}
