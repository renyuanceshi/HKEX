package com.hkex.soma.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.JSONParser.VideoJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.VideoAdapter;
import com.hkex.soma.basic.ClickControlContainer;
import com.hkex.soma.dataModel.VideoType_Result;
import com.hkex.soma.dataModel.Video_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;
import java.util.ArrayList;

public class Video extends AnimatedFragmentActivity {
    /* access modifiers changed from: private */
    public ArrayList<TextView> butlist = new ArrayList<>();
    /* access modifiers changed from: private */
    public Video_Result.mainData[] data = null;
    /* access modifiers changed from: private */
    public int fulllong = 0;
    /* access modifiers changed from: private */
    public HorizontalScrollView horizontalscrollview;
    /* access modifiers changed from: private */
    public ImageView left_arrow;
    /* access modifiers changed from: private */
    public ListView listView;
    private int maxScrollX;
    /* access modifiers changed from: private */
    public int maxitemlong = 0;
    private MenuContainer menu;
    /* access modifiers changed from: private */
    public ImageView right_arrow;
    /* access modifiers changed from: private */
    public LinearLayout tablist;
    /* access modifiers changed from: private */
    public LinearLayout tablist2;
    /* access modifiers changed from: private */
    public RelativeLayout tablist_box;

    public void dataResult(final Video_Result video_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (video_Result == null) {
                        String string = Video.this.getString(R.string.nodata_message);
                        Video.this.listView.setAdapter(new ArrayAdapter(Video.this, R.layout.list_nodata, new String[]{string}));
                    } else {
                        Video_Result.mainData[] unused = Video.this.data = video_Result.getmainData();
                        Video.this.listView.setAdapter(new VideoAdapter(Video.this, R.layout.list_video, Video.this.data));
                    }
                    Video.this.dataLoaded();
                    Video.this.listView.setDividerHeight(0);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void dataResult_type(final VideoType_Result videoType_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    Video.this.tablist.removeAllViews();
                    Video.this.butlist.clear();
                    for (int i2 = 0; i2 < videoType_Result.getMainData().length; i2++) {
                        TextView textView = new TextView(Video.this);
                        textView.setTag("" + videoType_Result.getMainData()[i2].getId());
                        textView.setPadding(10, 0, 10, 0);
                        textView.setText(videoType_Result.getMainData()[i2].getTitle());
                        textView.setGravity(17);
                        if (i2 == videoType_Result.getMainData().length - 1) {
                            textView.setBackgroundResource(R.drawable.tab_last_but);
                        } else {
                            textView.setBackgroundResource(R.drawable.tab_but);
                        }
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        textView.setTag(Integer.valueOf(i2));
                        textView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                int i = 0;
                                while (true) {
                                    int i2 = i;
                                    if (i2 >= Video.this.butlist.size()) {
                                        break;
                                    }
                                    if (i2 == videoType_Result.getMainData().length - 1) {
                                        ((TextView) Video.this.butlist.get(i2)).setBackgroundResource(R.drawable.tab_last_but);
                                    } else {
                                        ((TextView) Video.this.butlist.get(i2)).setBackgroundResource(R.drawable.tab_but);
                                    }
                                    i = i2 + 1;
                                }
                                if (Integer.parseInt(view.getTag().toString()) == videoType_Result.getMainData().length - 1) {
                                    ((TextView) Video.this.butlist.get(Integer.parseInt(view.getTag().toString()))).setBackgroundResource(R.drawable.tab_last_but_on);
                                } else {
                                    ((TextView) Video.this.butlist.get(Integer.parseInt(view.getTag().toString()))).setBackgroundResource(R.drawable.tab_but_on);
                                }
                                Video.this.loadJSON(videoType_Result.getMainData()[Integer.parseInt(view.getTag().toString())].getId());
                            }
                        });
                        textView.measure(0, 0);
                        int unused = Video.this.maxitemlong = Video.this.maxitemlong > textView.getMeasuredWidth() ? Video.this.maxitemlong : textView.getMeasuredWidth();
                        int unused2 = Video.this.fulllong = Video.this.fulllong + textView.getMeasuredWidth();
                        Video.this.butlist.add(textView);
                    }
                    if (Video.this.maxitemlong * Video.this.butlist.size() > Video.this.horizontalscrollview.getMeasuredWidth()) {
                        for (int i3 = 0; i3 < Video.this.butlist.size(); i3++) {
                            ((TextView) Video.this.butlist.get(i3)).setWidth(Video.this.maxitemlong);
                        }
                        while (i < Video.this.butlist.size()) {
                            Video.this.tablist.addView((View) Video.this.butlist.get(i));
                            i++;
                        }
                    } else {
                        Video.this.tablist_box.setVisibility(View.GONE);
                        Video.this.tablist2.setVisibility(View.VISIBLE);
                        while (i < Video.this.butlist.size()) {
                            ((TextView) Video.this.butlist.get(i)).setWidth(0);
                            ((TextView) Video.this.butlist.get(i)).setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
                            Video.this.tablist2.addView((View) Video.this.butlist.get(i));
                            i++;
                        }
                    }
                    if (Video.this.butlist.size() > 0) {
                        ((TextView) Video.this.butlist.get(0)).callOnClick();
                    }
                    Video.this.dataLoaded();
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void initUI() {
        setContentView(R.layout.video);
        this.menu = new MenuContainer(this);
        this.mainContainer = findViewById(R.id.mainContainer);
        this.clickControlContainer = (ClickControlContainer) findViewById(R.id.clickControlContainer);
        ClickControlContainer clickControlContainer = (ClickControlContainer) findViewById(R.id.menuContainer);
        clickControlContainer.addView(this.menu, new LinearLayout.LayoutParams(-1, -1));
        initFooter();
        updateFooterStime("");
        this.mainView = findViewById(R.id.appContainer);
        this.leftView = clickControlContainer;
        this.left_arrow = (ImageView) findViewById(R.id.left_arrow);
        this.right_arrow = (ImageView) findViewById(R.id.right_arrow);
        this.horizontalscrollview = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
        this.horizontalscrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            public void onScrollChanged() {
                int measuredWidth = Video.this.horizontalscrollview.getChildAt(0).getMeasuredWidth() - Video.this.horizontalscrollview.getMeasuredWidth();
                int scrollX = Video.this.horizontalscrollview.getScrollX();
                if (measuredWidth <= 0) {
                    Video.this.left_arrow.setVisibility(View.GONE);
                    Video.this.right_arrow.setVisibility(View.GONE);
                } else if (scrollX == 0) {
                    Video.this.left_arrow.setVisibility(View.GONE);
                    Video.this.right_arrow.setVisibility(View.VISIBLE);
                } else if (scrollX == measuredWidth) {
                    Video.this.left_arrow.setVisibility(View.VISIBLE);
                    Video.this.right_arrow.setVisibility(View.GONE);
                } else {
                    Video.this.left_arrow.setVisibility(View.VISIBLE);
                    Video.this.right_arrow.setVisibility(View.VISIBLE);
                }
            }
        });
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
        this.tablist = (LinearLayout) findViewById(R.id.tablist);
        this.listView = (ListView) findViewById(R.id.listView);
        this.tablist_box = (RelativeLayout) findViewById(R.id.tablist_box);
        this.tablist2 = (LinearLayout) findViewById(R.id.tablist2);
    }

    public void loadJSON(String str) {
        VideoJSONParser videoJSONParser = new VideoJSONParser();
        videoJSONParser.setOnJSONCompletedListener(new VideoJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Video_Result video_Result) {
                Video.this.dataResult(video_Result);
            }
        });
        videoJSONParser.setOnJSONFailedListener(new VideoJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Video.this.ShowConnectionErrorDialog(runnable);
                Video.this.dataResult((Video_Result) null);
            }
        });
        videoJSONParser.loadXML(getString(R.string.url_video_table) + "&id=" + str);
        dataLoading();
    }

    public void loadJSON_type() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(VideoType_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<VideoType_Result>() {
            public void OnJSONCompleted(VideoType_Result videoType_Result) {
                Video.this.dataResult_type(videoType_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                Video.this.dataResult_type((VideoType_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_video_type) + "&type=Category");
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
            loadJSON_type();
            return;
        }
        this.isMoving = false;
        Commons.noResumeAction = false;
    }
}
