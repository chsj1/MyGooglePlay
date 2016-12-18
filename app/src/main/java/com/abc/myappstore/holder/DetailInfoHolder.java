package com.abc.myappstore.holder;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.utils.StringUtils;
import com.abc.myappstore.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 15:09
 * 描述	      ${TODO}
 *
 */
public class DetailInfoHolder extends BaseHolder<ItemInfoBean> {


    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
        //just findView


        return holderView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {

//        mAppDetailInfoTvName.setText(data.name);

    }
}
