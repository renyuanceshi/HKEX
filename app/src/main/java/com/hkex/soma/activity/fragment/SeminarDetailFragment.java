package com.hkex.soma.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.activity.WebViewPage;
import com.hkex.soma.adapter.SeminarListAdapter;
import com.hkex.soma.basic.MasterFragment;
import com.hkex.soma.dataModel.Seminar_Result;
import com.hkex.soma.element.MenuContainer;
import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;
import com.hkex.soma.slideAnimator.SlideRightAnimationHandler;
import com.hkex.soma.utils.Commons;

public class SeminarDetailFragment extends MasterFragment {
    public static final String TAG = "SeminarDetailFragment";
    private static SeminarDetailFragment fragment;
    /* access modifiers changed from: private */
    public AnimatedFragmentActivity _self;
    /* access modifiers changed from: private */
    public TextView date;
    /* access modifiers changed from: private */
    public TextView detail;
    /* access modifiers changed from: private */
    public ImageView enroll;
    private View fragmentView;
    private Handler handler = new Handler();
    /* access modifiers changed from: private */
    public String id = "";
    private ImageButton leftbtn;
    private ListView listview;
    private MenuContainer menu;
    private ImageButton rightbtn;
    private SeminarListAdapter seminarlistadapter;
    private SlideRightAnimationHandler slideRightAnimationHandler;
    /* access modifiers changed from: private */
    public TextView speakers;
    /* access modifiers changed from: private */
    public TextView time;
    /* access modifiers changed from: private */
    public TextView title;
    /* access modifiers changed from: private */
    public String type = "";
    /* access modifiers changed from: private */
    public TextView venue;

    private void getview() {
        this.leftbtn = (ImageButton) this.fragmentView.findViewById(R.id.btnLeft);
        this.title = (TextView) this.fragmentView.findViewById(R.id.title);
        this.date = (TextView) this.fragmentView.findViewById(R.id.date);
        this.time = (TextView) this.fragmentView.findViewById(R.id.time);
        this.venue = (TextView) this.fragmentView.findViewById(R.id.venue);
        this.speakers = (TextView) this.fragmentView.findViewById(R.id.speakers);
        this.detail = (TextView) this.fragmentView.findViewById(R.id.detail);
        this.enroll = (ImageView) this.fragmentView.findViewById(R.id.enroll);
    }

    public static SeminarDetailFragment newInstance(String str) {
        fragment = new SeminarDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setlistener() {
        this.enroll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SeminarDetailFragment.this.type.equals("Inapp")) {
                    Intent intent = new Intent().setClass(SeminarDetailFragment.this._self, WebViewPage.class);
                    intent.putExtra("title", "");
                    intent.putExtra("url", view.getTag().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Commons.noResumeAction = true;
                    SeminarDetailFragment.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(view.getTag().toString()));
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Commons.noResumeAction = true;
                SeminarDetailFragment.this.startActivity(intent2);
            }
        });
    }

    public void dataResult(final Seminar_Result seminar_Result) {
        this.handler.post(new Runnable() {
            public void run() {
                try {
                    SeminarDetailFragment.this._self.dataLoaded();
                    for (int i = 0; i < seminar_Result.getMainData().length; i++) {
                        if (seminar_Result.getMainData()[i].getSid().equals(SeminarDetailFragment.this.id)) {
                            SeminarDetailFragment.this.title.setText(seminar_Result.getMainData()[i].getTitle());
                            SeminarDetailFragment.this.date.setText(String.format(SeminarDetailFragment.this.getString(R.string.seminar_date), new Object[]{seminar_Result.getMainData()[i].getDate()}));
                            SeminarDetailFragment.this.time.setText(String.format(SeminarDetailFragment.this.getString(R.string.seminar_time), new Object[]{seminar_Result.getMainData()[i].getTime()}));
                            SeminarDetailFragment.this.venue.setText(String.format(SeminarDetailFragment.this.getString(R.string.seminar_venue), new Object[]{seminar_Result.getMainData()[i].getVenue()}));
                            SeminarDetailFragment.this.speakers.setText(String.format(SeminarDetailFragment.this.getString(R.string.seminar_speakers), new Object[]{seminar_Result.getMainData()[i].getSpeaker()}));
                            SeminarDetailFragment.this.detail.setText(seminar_Result.getMainData()[i].getDetail());
                            String unused = SeminarDetailFragment.this.type = seminar_Result.getMainData()[i].getType();
                            SeminarDetailFragment.this.enroll.setTag(seminar_Result.getMainData()[i].getLink());
                            return;
                        }
                    }
                } catch (Exception e) {
                    SeminarDetailFragment.this._self.dataLoaded();
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadJSON() {
        GenericJSONParser genericJSONParser = new GenericJSONParser(Seminar_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<Seminar_Result>() {
            public void OnJSONCompleted(Seminar_Result seminar_Result) {
                SeminarDetailFragment.this.dataResult(seminar_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                exc.printStackTrace();
                SeminarDetailFragment.this.dataResult((Seminar_Result) null);
            }
        });
        genericJSONParser.loadXML(getString(R.string.url_seminar));
        Log.v(TAG, "url " + getString(R.string.url_seminar));
        this._self.dataLoading();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._self = (AnimatedFragmentActivity) getActivity();
        this.id = getArguments().getString("id");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.fragmentView = layoutInflater.inflate(R.layout.seminar_detail_frag, viewGroup, false);
        try {
            getview();
            setlistener();
            loadJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.fragmentView;
    }
}
