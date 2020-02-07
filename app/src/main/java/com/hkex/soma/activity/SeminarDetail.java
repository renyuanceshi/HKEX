package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.Seminar_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;

public class SeminarDetail extends AnimatedFragmentActivity {
    public static final String TAG = "SeminarDetail";
    private Activity _self = this;
    private TextView date;
    private TextView detail;
    private ImageView enroll;
    private String id = "";
    private ImageButton leftbtn;
    private ListView listview;
    private MenuContainer menu;
    private ImageButton rightbtn;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    private TextView speakers;
    private TextView time;
    private TextView title;
    private String type = "";
    private TextView venue;

    private void getview() {
        this.leftbtn = (ImageButton) findViewById(R.id.btnLeft);
        this.rightbtn = (ImageButton) findViewById(R.id.btnright);
        this.canBack = true;
        this.title = (TextView) findViewById(R.id.title);
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
        this.venue = (TextView) findViewById(R.id.venue);
        this.speakers = (TextView) findViewById(R.id.speakers);
        this.detail = (TextView) findViewById(R.id.detail);
        this.enroll = (ImageView) findViewById(R.id.enroll);
    }

    private void setlistener() {
        this.enroll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SeminarDetail.this.type.equals("Inapp")) {
                    Intent intent = new Intent().setClass(SeminarDetail.this, WebViewPage.class);
                    intent.putExtra("title", SeminarDetail.this.getString(R.string.head_seminar));
                    intent.putExtra("url", view.getTag().toString());
                    SeminarDetail.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(view.getTag().toString()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SeminarDetail.this.startActivity(intent2);
            }
        });
    }

    public void dataResult(final Seminar_Result seminar_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    SeminarDetail.this.dataLoaded();
                    for (int i = 0; i < seminar_Result.getMainData().length; i++) {
                        if (seminar_Result.getMainData()[i].getSid().equals(SeminarDetail.this.id)) {
                            SeminarDetail.this.title.setText(seminar_Result.getMainData()[i].getTitle());
                            SeminarDetail.this.date.setText(String.format(SeminarDetail.this.getString(R.string.seminar_date), new Object[]{seminar_Result.getMainData()[i].getDate()}));
                            SeminarDetail.this.time.setText(String.format(SeminarDetail.this.getString(R.string.seminar_time), new Object[]{seminar_Result.getMainData()[i].getTime()}));
                            SeminarDetail.this.venue.setText(String.format(SeminarDetail.this.getString(R.string.seminar_venue), new Object[]{seminar_Result.getMainData()[i].getVenue()}));
                            SeminarDetail.this.speakers.setText(String.format(SeminarDetail.this.getString(R.string.seminar_speakers), new Object[]{seminar_Result.getMainData()[i].getSpeaker()}));
                            SeminarDetail.this.detail.setText(seminar_Result.getMainData()[i].getDetail());
                            String unused = SeminarDetail.this.type = seminar_Result.getMainData()[i].getType();
                            SeminarDetail.this.enroll.setTag(seminar_Result.getMainData()[i].getLink());
                            return;
                        }
                    }
                } catch (Exception e) {
                    SeminarDetail.this.dataLoaded();
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
                SeminarDetail.this.dataResult(seminar_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                SeminarDetail.this.dataResult((Seminar_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_seminar));
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.id = getIntent().getStringExtra("id");
        setContentView(R.layout.seminar_detail);
        Commons.noResumeAction = true;
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
            Commons.noResumeAction = true;
        }
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
        this.leftbtn.setImageResource(R.drawable.btn_back);
        this.leftbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                SeminarDetail.this.finish();
            }
        });
        this.clickControlContainer.setOnLeftToRightSwipeListener(new ClickControlContainer.OnLeftToRightSwipeListener() {
            public void onLeftToRightSwiped() {
                slideLeftAnimationHandler.onClick((View) null);
            }
        });
        this.clickControlContainer.setOnRightToLeftSwipeListener(new ClickControlContainer.OnRightToLeftSwipeListener() {
            public void onRightToLeftSwiped() {
                if (SeminarDetail.this.leftViewOut || SeminarDetail.this.rightViewOut) {
                    SeminarDetail.this.slideRightAnimationHandler.onClick((View) null);
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
