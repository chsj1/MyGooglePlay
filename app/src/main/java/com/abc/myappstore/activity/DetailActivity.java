package com.abc.myappstore.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.abc.myappstore.R;
import com.abc.myappstore.base.LoadingPager;
import com.abc.myappstore.bean.ItemInfoBean;
import com.abc.myappstore.holder.DetailDesHolder;
import com.abc.myappstore.holder.DetailDownloadHolder;
import com.abc.myappstore.holder.DetailInfoHolder;
import com.abc.myappstore.holder.DetailPicHolder;
import com.abc.myappstore.holder.DetailSafeHolder;
import com.abc.myappstore.manager.DownLoadInfo;
import com.abc.myappstore.manager.DownLoadManager;
import com.abc.myappstore.protocol.DetailProtocol;
import com.abc.myappstore.utils.LogUtils;
import com.abc.myappstore.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者     Chris
 * 创建时间   2016/7/11 14:19
 * 描述	      视图-->应该展示4种视图类型中的一种(加载中,成功,失败,空)
 * 描述	      数据加载-->加载具体应用的详情数据
 * 描述	      动态添加和移除观察者
 *
 */
public class DetailActivity extends AppCompatActivity {

    @InjectView(R.id.item_detail_download_container)
    FrameLayout mItemDetailDownloadContainer;
    @InjectView(R.id.item_detail_info_container)
    FrameLayout mItemDetailInfoContainer;
    @InjectView(R.id.item_detail_safe_container)
    FrameLayout mItemDetailSafeContainer;
    @InjectView(R.id.item_detail_pic_container)
    FrameLayout mItemDetailPicContainer;
    @InjectView(R.id.item_detail_des_container)
    FrameLayout mItemDetailDesContainer;
    private String               mPackageName;
    private String               mName;
    private LoadingPager         mLoadingPager;
    private ItemInfoBean         mItemInfoBean;
    private DetailDownloadHolder mDetailDownloadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initActionBar();
        initView();
        initData();
    }

    /**
     * 处理视图
     */
    private void initView() {
        mLoadingPager = new LoadingPager(this) {
            @Override
            public LoadedResult initData() {
                return DetailActivity.this.onLoadData();
            }

            @Override
            public View initSuccessView() {
                return DetailActivity.this.initSuccessView();
            }
        };
        setContentView(mLoadingPager);
    }

    /**
     * 决定成功视图长什么样子
     * 数据和视图的绑定操作
     *
     * @return
     */
    private View initSuccessView() {
        View successView = View.inflate(UIUtils.getContext(), R.layout.item_detail, null);
        //找出容器
        ButterKnife.inject(this, successView);


        //应用的 信息 部分
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        mItemDetailInfoContainer.addView(detailInfoHolder.mHolderView);
        detailInfoHolder.setDataAndRefreshHolderView(mItemInfoBean);


        //应用的 安全 部分
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        mItemDetailSafeContainer.addView(detailSafeHolder.mHolderView);
        detailSafeHolder.setDataAndRefreshHolderView(mItemInfoBean);


        //应用的 截图 部分
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        mItemDetailPicContainer.addView(detailPicHolder.mHolderView);
        detailPicHolder.setDataAndRefreshHolderView(mItemInfoBean);


        //应用的 描述 部分
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        mItemDetailDesContainer.addView(detailDesHolder.mHolderView);
        detailDesHolder.setDataAndRefreshHolderView(mItemInfoBean);


        //应用的 下载 部分
        mDetailDownloadHolder = new DetailDownloadHolder();
        mItemDetailDownloadContainer.addView(mDetailDownloadHolder.mHolderView);
        mDetailDownloadHolder.setDataAndRefreshHolderView(mItemInfoBean);

        //添加观察者到观察者集合中
        DownLoadManager.getInstance().addObserver(mDetailDownloadHolder);

        return successView;
    }

    /**
     * 真正的在子线程中完成数据的加载
     *
     * @return
     */
    private LoadingPager.LoadedResult onLoadData() {
        //        SystemClock.sleep(2000);

        DetailProtocol protocol = new DetailProtocol(mPackageName);
        try {
            mItemInfoBean = protocol.loadData(0);
            if (mItemInfoBean != null) {
                return LoadingPager.LoadedResult.SUCCESS;
            } else {
                return LoadingPager.LoadedResult.ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    /**
     * 加载数据
     */
    private void initData() {
        mLoadingPager.triggerLoadData();
    }


    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
        mName = getIntent().getStringExtra("name");
        Toast.makeText(getApplicationContext(), mPackageName, Toast.LENGTH_SHORT).show();
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(mName);

        //显示回退按钮
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        //添加观察者到观察者集合中
        if (mDetailDownloadHolder != null) {
            LogUtils.sf("添加观察者");
            DownLoadManager.getInstance().addObserver(mDetailDownloadHolder);

            //手动发布最新的DownLoadInfo状态
            DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mItemInfoBean);
            DownLoadManager.getInstance().notifyObservers(downLoadInfo);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //从观察者集合中移除观察者
        if (mDetailDownloadHolder != null) {
            LogUtils.sf("移除观察者");
            DownLoadManager.getInstance().deleteObserver(mDetailDownloadHolder);
        }
        super.onPause();
    }
}
