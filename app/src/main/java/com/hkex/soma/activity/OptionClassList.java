package com.hkex.soma.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.ClassListJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.ClassListAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.basic.MultiScrollListView;
import com.hkex.soma.basic.MultiScrollView;
import com.hkex.soma.dataModel.ClassList;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import org.codehaus.jackson.util.MinimalPrettyPrinter;

public class OptionClassList extends AnimatedFragmentActivity {
    /* access modifiers changed from: private */
    public ClassList.mainData[] data = null;
    /* access modifiers changed from: private */
    public MultiScrollListView listView;
    private MenuContainer menu;
    /* access modifiers changed from: private */
    public MultiScrollView scrollView;

    public void dataResult(final ClassList classList) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    TextView textView = (TextView) OptionClassList.this.findViewById(R.id.numofclasses);
                    if (classList == null) {
                        String string = OptionClassList.this.getString(R.string.nodata_message);
                        OptionClassList.this.listView.setAdapter(new ArrayAdapter(OptionClassList.this, R.layout.list_nodata, new String[]{string}));
                        textView.setText(OptionClassList.this.getString(R.string.no_data));
                        OptionClassList.this.updateFooterStime_nodelay(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    } else {
                        ClassList.mainData[] unused = OptionClassList.this.data = classList.getmainData();
                        OptionClassList.this.listView.setAdapter(new ClassListAdapter(OptionClassList.this, R.layout.list_classlist, OptionClassList.this.data));
                        textView.setText(OptionClassList.this.data.length + "");
                        OptionClassList.this.updateFooterStime_nodelay(OptionClassList.this.data[0].getStime());
                    }
                    OptionClassList.this.dataLoaded();
                    OptionClassList.this.listView.setDividerHeight(0);
                    OptionClassList.this.scrollView.initMultiScrollView(OptionClassList.this.listView, 159.0f, 0.0f);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        setContentView(R.layout.optionclasslist);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        this.mainView = findViewById(R.id.appContainer);
        this.leftView = clickControlContainer;
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.mainView.findViewById(R.id.btnLeft).setOnClickListener(slideLeftAnimationHandler);
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.listView = (MultiScrollListView) findViewById(R.id.listView);
        this.scrollView = (MultiScrollView) findViewById(2131165570);
    }

    public void loadJSON() {
        ClassListJSONParser classListJSONParser = new ClassListJSONParser();
        classListJSONParser.setOnJSONCompletedListener(new ClassListJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(ClassList classList) {
                Commons.LogDebug("", classList.toString());
                OptionClassList.this.dataResult(classList);
            }
        });
        classListJSONParser.setOnJSONFailedListener(new ClassListJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                OptionClassList.this.ShowConnectionErrorDialog(runnable);
                OptionClassList.this.dataResult((ClassList) null);
            }
        });
        classListJSONParser.loadXML(getString(R.string.url_option_class));
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!Commons.noResumeAction) {
            this.leftViewOut = false;
            this.rightViewOut = false;
            initUI();
            loadJSON();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }
}
