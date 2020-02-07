package com.hkex.soma.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import java.util.HashMap;

public class TabManager implements TabHost.OnTabChangeListener {
    private boolean canshowpage = true;
    private final FragmentActivity mActivity;
    private final int mContainerId;
    TabInfo mLastTab;
    private final TabHost mTabHost;
    private final HashMap<String, TabInfo> mTabs = new HashMap<>();

    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String str) {
            View view = new View(this.mContext);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    static final class TabInfo {
        /* access modifiers changed from: private */
        public final Bundle args;
        /* access modifiers changed from: private */
        public final Class<?> clss;
        /* access modifiers changed from: private */
        public Fragment fragment;
        /* access modifiers changed from: private */
        public final String tag;

        TabInfo(String str, Class<?> cls, Bundle bundle) {
            this.tag = str;
            this.clss = cls;
            this.args = bundle;
        }
    }

    public TabManager(FragmentActivity fragmentActivity, TabHost tabHost, int i) {
        this.mActivity = fragmentActivity;
        this.mTabHost = tabHost;
        this.mContainerId = i;
        this.mTabHost.setOnTabChangedListener(this);
    }

    public TabManager(FragmentActivity fragmentActivity, TabHost tabHost, int i, boolean z) {
        this.mActivity = fragmentActivity;
        this.mTabHost = tabHost;
        this.mContainerId = i;
        this.mTabHost.setOnTabChangedListener(this);
        this.canshowpage = z;
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> cls, Bundle bundle) {
        tabSpec.setContent(new DummyTabFactory(this.mActivity));
        String tag = tabSpec.getTag();
        TabInfo tabInfo = new TabInfo(tag, cls, bundle);
        Fragment unused = tabInfo.fragment = this.mActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction beginTransaction = this.mActivity.getSupportFragmentManager().beginTransaction();
            beginTransaction.detach(tabInfo.fragment);
            beginTransaction.commit();
        }
        this.mTabs.put(tag, tabInfo);
        this.mTabHost.addTab(tabSpec);
    }

    public void onTabChanged(String str) {
        TabInfo tabInfo;
        if (this.canshowpage && this.mLastTab != (tabInfo = this.mTabs.get(str))) {
            FragmentTransaction beginTransaction = this.mActivity.getSupportFragmentManager().beginTransaction();
            if (!(this.mLastTab == null || this.mLastTab.fragment == null)) {
                beginTransaction.detach(this.mLastTab.fragment);
            }
            if (tabInfo != null) {
                Fragment unused = tabInfo.fragment = Fragment.instantiate(this.mActivity, tabInfo.clss.getName(), tabInfo.args);
                beginTransaction.add(this.mContainerId, tabInfo.fragment, tabInfo.tag);
                if (tabInfo.fragment == null) {
                    beginTransaction.detach(this.mLastTab.fragment);
                } else {
                    this.mActivity.getSupportFragmentManager().popBackStack();
                    beginTransaction.replace(this.mContainerId, tabInfo.fragment);
                    beginTransaction.attach(tabInfo.fragment);
                }
            }
            this.mLastTab = tabInfo;
            beginTransaction.commit();
            this.mActivity.getSupportFragmentManager().executePendingTransactions();
        }
    }

    public void setcanshowpage(boolean z) {
        this.canshowpage = z;
    }
}
