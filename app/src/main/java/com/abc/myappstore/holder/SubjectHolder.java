package com.abc.myappstore.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.SubjectBean;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/9 15:56
 * 描述	      ${TODO}
 *
 */
public class SubjectHolder extends BaseHolder<SubjectBean> {
    @InjectView(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @InjectView(R.id.item_subject_tv_title)
    TextView  mItemSubjectTvTitle;

    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        //找孩子
        ButterKnife.inject(this, holderView);
        return holderView;
    }

    @Override
    public void refreshHolderView(SubjectBean data) {
        mItemSubjectTvTitle.setText(data.des);
        String url = Constants.URLS.IMGBASEURL + data.url;
        ImageLoader.getInstance().displayImage(url, mItemSubjectIvIcon);
    }
}
