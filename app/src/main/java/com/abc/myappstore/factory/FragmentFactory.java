package com.abc.myappstore.factory;

import com.abc.myappstore.base.BaseFragment;
import com.abc.myappstore.fragment.AppFragment;
import com.abc.myappstore.fragment.CategoryFragment;
import com.abc.myappstore.fragment.GameFragment;
import com.abc.myappstore.fragment.HomeFragment;
import com.abc.myappstore.fragment.HotFragment;
import com.abc.myappstore.fragment.RecommendFragment;
import com.abc.myappstore.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     chris
 * 创建时间   2016/7/6 08:51
 * 描述	      ${TODO}
 *
 */
public class FragmentFactory {

    public static final int FRAGMENT_HOME      = 0;//首页
    public static final int FRAGMENT_APP       = 1;//应用
    public static final int FRAGMENT_GAME      = 2;//游戏
    public static final int FRAGMENT_SUBJECT   = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY  = 5;//分类
    public static final int FRAGMENT_HOT       = 6;//排行

    public static Map<Integer, BaseFragment> mCacheFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        BaseFragment fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();

                break;
            case FRAGMENT_GAME:
                fragment = new GameFragment();

                break;
            case FRAGMENT_SUBJECT:
                fragment = new SubjectFragment();

                break;
            case FRAGMENT_RECOMMEND:
                fragment = new RecommendFragment();

                break;
            case FRAGMENT_CATEGORY:
                fragment = new CategoryFragment();

                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();

                break;

            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
