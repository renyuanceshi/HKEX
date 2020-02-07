package com.hkex.soma.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.SeminarListAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.Seminar_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class Seminar extends AnimatedFragmentActivity {
    public static final String TAG = "Seminar";
    /* access modifiers changed from: private */
    public Activity _self = this;
    /* access modifiers changed from: private */
    public ArrayList<Seminar_Result.mainData> data = new ArrayList<>();
    private Handler handler = new Handler();
    private ImageButton leftbtn;
    private ListView listview;
    private MenuContainer menu;
    private ImageButton rightbtn;
    /* access modifiers changed from: private */
    public SeminarListAdapter seminarlistadapter;
    /* access modifiers changed from: private */
    public SlideRightAnimationHandler slideRightAnimationHandler;

    private void getview() {
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.rightbtn = (ImageButton) findViewById(R.id.btnright);
        this.listview = (ListView) findViewById(R.id.listview);
        this.seminarlistadapter = new SeminarListAdapter(this._self, this.data);
        this.listview.setAdapter(this.seminarlistadapter);
    }

    private void setlistener() {
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(Seminar.this._self, SeminarDetail.class);
                intent.putExtra("id", ((Seminar_Result.mainData) Seminar.this.data.get(i)).getSid());
                Seminar.this._self.startActivity(intent);
            }
        });
    }

    public void dataResult(final Seminar_Result seminar_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    Seminar.this.data.clear();
                    for (Seminar_Result.mainData add : seminar_Result.getMainData()) {
                        Seminar.this.data.add(add);
                    }
                    Seminar.this.seminarlistadapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Seminar.this.dataLoaded();
                    e.printStackTrace();
                }
            }
        });
    }

    public void halfPortfolioUpdated() {
        this.slideRightAnimationHandler.onClick((View) null);
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(Seminar_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Seminar_Result>() {
            public void OnJSONCompleted(Seminar_Result seminar_Result) {
                Seminar.this.dataResult(seminar_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                Seminar.this.dataResult((Seminar_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_seminar));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.seminar_list);
        try {
            getview();
            setlistener();
            setmenu();
            loadJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (Commons.noResumeAction) {
            this.isMoving = false;
            Commons.noResumeAction = false;
        }
        setmenu();
    }

    public void setmenu() {
        this.rightbtn.setVisibility(View.INVISIBLE);
        this.leftViewOut = false;
        this.rightViewOut = false;
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        this.leftView = clickControlContainer;
        this.mainView = findViewById(R.id.appContainer);
        this.slideRightAnimationHandler = new SlideRightAnimationHandler(this);
        final SlideLeftAnimationHandler slideLeftAnimationHandler = new SlideLeftAnimationHandler(this);
        this.leftbtn.setOnClickListener(slideLeftAnimationHandler);
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (Seminar.this.leftViewOut || Seminar.this.rightViewOut) {
                    Seminar.this.slideRightAnimationHandler.onClick((View) null);
                }
            }
        });
        clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.menu.setSliedBackHandler(slideLeftAnimationHandler);
    }
}
