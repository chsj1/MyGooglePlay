package com.abc.myappstore.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.utils.UIUtils;
import com.abc.myappstore.views.RatioLayout;
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
public class DetailPicHolder extends BaseHolder<ItemInfoBean> {

    @InjectView(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        ButterKnife.inject(this, holderView);
        return holderView;

    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        List<String> stringList = data.screen;
        for (int i = 0; i < stringList.size(); i++) {
            //data
            String url = stringList.get(i);

            ImageView ivPic = new ImageView(UIUtils.getContext());
            //加载图片
            ImageLoader.getInstance().displayImage(Constants.URLS.IMGBASEURL + url, ivPic);


            RatioLayout rl = new RatioLayout(UIUtils.getContext());
            //RatioLayout属性设置
            rl.setRelative(RatioLayout.RELATIVE_WIDTH);
            rl.setPicRatio((float) 150 / 250);
            rl.addView(ivPic);

            int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth-UIUtils.dip2Px(16);
            int width = screenWidth / 3 ;//已知
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;//动态计算


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);


            if (i != 0) {
                params.leftMargin = UIUtils.dip2Px(4);
            }

            mAppDetailPicIvContainer.addView(rl, params);
        }

    }
}
