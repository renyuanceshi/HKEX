package com.hkex.soma.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.hkex.soma.JSONParser.VideoJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.adapter.VideoAdapter;
import com.hkex.soma.basic.MasterActivity;
import com.hkex.soma.dataModel.Video_Result;
import com.hkex.soma.utils.Commons;

public class TVInterviews extends MasterActivity {
    /* access modifiers changed from: private */
    public Video_Result.mainData[] data = null;
    /* access modifiers changed from: private */
    public ListView listView;

    public void dataResult(final Video_Result video_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    if (video_Result == null) {
                        String string = TVInterviews.this.getString(R.string.nodata_message);
                        TVInterviews.this.listView.setAdapter(new ArrayAdapter(TVInterviews.this, R.layout.list_nodata, new String[]{string}));
                    } else {
                        Video_Result.mainData[] unused = TVInterviews.this.data = video_Result.getmainData();
                        TVInterviews.this.listView.setAdapter(new VideoAdapter(TVInterviews.this, R.layout.list_video, TVInterviews.this.data));
                    }
                    TVInterviews.this.dataLoaded();
                    TVInterviews.this.listView.setDividerHeight(0);
                } catch (Exception e) {
                    Commons.LogDebug(toString(), e.getMessage());
                }
            }
        });
    }

    public void initUI() {
        setContentView(R.layout.video);
        initFooter();
        updateFooterStime("");
        ((TextView) findViewById(R.id.pageTitle)).setText(R.string.title_tvinterviews);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnLeft);
        imageButton.setImageResource(R.drawable.btn_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                TVInterviews.this.finish();
            }
        });
        this.listView = (ListView) findViewById(R.id.listView);
    }

    public void loadJSON() {
        VideoJSONParser videoJSONParser = new VideoJSONParser();
        videoJSONParser.setOnJSONCompletedListener(new VideoJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(Video_Result video_Result) {
                TVInterviews.this.dataResult(video_Result);
            }
        });
        videoJSONParser.setOnJSONFailedListener(new VideoJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                TVInterviews.this.ShowConnectionErrorDialog(runnable);
                TVInterviews.this.dataResult((Video_Result) null);
            }
        });
        videoJSONParser.loadXML(getString(R.string.url_tvInterview));
        dataLoading();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initUI();
        loadJSON();
    }
}
