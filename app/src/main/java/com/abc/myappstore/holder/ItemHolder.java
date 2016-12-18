package com.abc.myappstore.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abc.myappstore.R;
import com.abc.myappstore.base.BaseHolder;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.conf.Constants;
import com.abc.myappstore.factory.DisplayImageOptionsFactory;
import com.abc.myappstore.manager.DownLoadInfo;
import com.abc.myappstore.manager.DownLoadManager;
import com.abc.myappstore.utils.CommonUtils;
import com.abc.myappstore.utils.PrintDownLoadInfo;
import com.abc.myappstore.utils.StringUtils;
import com.abc.myappstore.utils.UIUtils;
import com.abc.myappstore.views.ProgressView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 创建者     Chris
 * 创建时间   2016/7/6 16:11
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 * 描述	      针对的是HomeAdapter
 *
 */
public class ItemHolder extends BaseHolder<ItemInfoBean> implements DownLoadManager.DownLoadInfoObserver{
    @InjectView(R.id.item_appinfo_iv_icon)
    ImageView    mItemAppinfoIvIcon;
    @InjectView(R.id.item_appinfo_tv_title)
    TextView     mItemAppinfoTvTitle;
    @InjectView(R.id.item_appinfo_rb_stars)
    RatingBar    mItemAppinfoRbStars;
    @InjectView(R.id.item_appinfo_tv_size)
    TextView     mItemAppinfoTvSize;
    @InjectView(R.id.item_appinfo_tv_des)
    TextView     mItemAppinfoTvDes;
    @InjectView(R.id.item_progressView)
    ProgressView mItemProgressView;
    private ItemInfoBean mItemInfoBean;

    /**
     * 1.决定根视图长什么样子
     * 2.找出孩子
     *
     * @return
     */
    @Override
    public View initHolderViewAndFindViews() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        //找孩子
        ButterKnife.inject(this, holderView);

        return holderView;
    }

    /**
     * 进行数据和视图的绑定
     *
     * @param data
     */
    @Override
    public void refreshHolderView(ItemInfoBean data) {


        //置空progressView的进度
        mItemProgressView.setProgress(0);//-->invalidate-->ondraw

        //保存数据到成员变量
        mItemInfoBean = data;

        mItemAppinfoTvTitle.setText(data.name);
        mItemAppinfoTvDes.setText(data.des);
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));

        mItemAppinfoRbStars.setRating(data.stars);
        //图片加载暂时没有加载
        String url = Constants.URLS.IMGBASEURL + data.iconUrl;

        ImageLoader.getInstance().displayImage(url, mItemAppinfoIvIcon, DisplayImageOptionsFactory.createDefaultOptions());
        
        
        /*--------------- 根据不同的状态,展示不同的ui(progressView) ---------------*/
        //curState-->downLoadInfo
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(data);
        refreshProgressViewUI(downLoadInfo);
    }

    private void refreshProgressViewUI(DownLoadInfo downLoadInfo) {
        int curState = downLoadInfo.curState;
        /*
        状态(编程记录)  	|  给用户的提示(ui展现)
        ----------------|-----------------------
        未下载			|下载
        下载中			|显示进度条
        暂停下载			|继续下载
        等待下载			|等待中...
        下载失败 		|重试
        下载完成 		|安装
        已安装 			|打开

         */
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD://未下载
                mItemProgressView.setNote("下载");
                mItemProgressView.setIcon(R.drawable.ic_download);
                break;
            case DownLoadManager.STATE_DOWNLOADING://下载中
                mItemProgressView.setIcon(R.drawable.ic_pause);

                mItemProgressView.setProgressEnable(true);
                int index = (int) (downLoadInfo.progress * 1.0f / downLoadInfo.max * 100 + .5f);
                mItemProgressView.setNote(index + "%");
                mItemProgressView.setMax(downLoadInfo.max);
                mItemProgressView.setProgress(downLoadInfo.progress);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD://暂停下载
                mItemProgressView.setIcon(R.drawable.ic_resume);

                mItemProgressView.setNote("继续");
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD://等待中
                mItemProgressView.setIcon(R.drawable.ic_pause);

                mItemProgressView.setNote("等待");
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED://下载失败
                mItemProgressView.setIcon(R.drawable.ic_redownload);

                mItemProgressView.setNote("重试");
                break;
            case DownLoadManager.STATE_DOWNLOADED://下载完成
                mItemProgressView.setIcon(R.drawable.ic_install);

                mItemProgressView.setNote("安装");
                mItemProgressView.setProgressEnable(false);
                break;
            case DownLoadManager.STATE_INSTALLED://已安装
                mItemProgressView.setIcon(R.drawable.ic_install);

                mItemProgressView.setNote("打开");
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.item_progressView)
    public void clickProgressView(View view) {
         /*--------------- 根据不同的状态,触发不同的操作 ---------------*/
        //curState-->downLoadInfo
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mItemInfoBean);
        int curState = downLoadInfo.curState;
        /*

            状态(编程记录)   | 用户行为(触发操作)
            --------------- | -----------------
            未下载			| 去下载
            下载中			| 暂停下载
            暂停下载			| 断点继续下载
            等待下载			| 取消下载
            下载失败 		| 重试下载
            下载完成 		| 安装应用
            已安装 			| 打开应用
         */
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD://未下载
                downLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADING://下载中
                pauseDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD://暂停下载
                downLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD://等待中
                cancelDownLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED://下载失败
                downLoad(downLoadInfo);
                break;
            case DownLoadManager.STATE_DOWNLOADED://下载完成
                installApk(downLoadInfo);
                break;
            case DownLoadManager.STATE_INSTALLED://已安装
                openApk(downLoadInfo);
                break;

            default:
                break;
        }
    }

    /**
     * 打开apk
     *
     * @param downLoadInfo
     */
    private void openApk(DownLoadInfo downLoadInfo) {
        CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
    }

    /**
     * 安装apk
     *
     * @param downLoadInfo
     */
    private void installApk(DownLoadInfo downLoadInfo) {
        File apkFile = new File(downLoadInfo.savePath);
        CommonUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 取消下载
     *
     * @param downLoadInfo
     */
    private void cancelDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().cancelDownLoad(downLoadInfo);
    }

    /**
     * 暂停下载
     *
     * @param downLoadInfo
     */
    private void pauseDownLoad(DownLoadInfo downLoadInfo) {
        DownLoadManager.getInstance().pauseDownLoad(downLoadInfo);
    }

    /**
     * 开始下载,继续下载,重试下载
     *
     * @param downLoadInfo
     */
    private void downLoad(DownLoadInfo downLoadInfo) {
     /*   Toast.makeText(UIUtils.getContext(), "开始下载", Toast.LENGTH_SHORT).show();
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        String dir = FileUtils.getDir("apk");
        String fileName = mItemInfoBean.packageName + ".apk";
        File saveFile = new File(dir, fileName);

        //downLoadInfo的赋值
        downLoadInfo.downLoadUrl = mItemInfoBean.downloadUrl;
        downLoadInfo.savePath = saveFile.getAbsolutePath();*/

        DownLoadManager.getInstance().downLoad(downLoadInfo);
    }

    @Override
    public void onDownLoadInfoChanged(final DownLoadInfo downLoadInfo) {
        if(!downLoadInfo.packageName.equals(mItemInfoBean.packageName)){
            return;
        }

        PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
        //更新ui
        UIUtils.getMainThreadHanlder().post(new Runnable() {
            @Override
            public void run() {
                refreshProgressViewUI(downLoadInfo );
            }
        });
    }
}
