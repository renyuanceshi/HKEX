package com.hkex.soma.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dbpower.urlimageviewhelper.UrlImageViewHelper;
import com.hkex.soma.R;
import com.hkex.soma.dataModel.Video_Result;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.StringFormatter;

public class VideoAdapter extends ArrayAdapter {
    private Context context;
    private Video_Result.mainData[] item = null;

    static class ViewHolder {
        private ImageView imageView;
        private TextView video_date;
        private TextView video_duration;
        private TextView video_title;
        private LinearLayout videolayout;

        ViewHolder() {
        }
    }

    public VideoAdapter(Context context2, int i, Video_Result.mainData[] maindataArr) {
        super(context2, i, maindataArr);
        this.context = context2;
        this.item = maindataArr;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_video, (ViewGroup) null);
            ViewHolder viewHolder2 = new ViewHolder();
            TextView unused = viewHolder2.video_title = (TextView) view.findViewById(R.id.video_title);
            TextView unused2 = viewHolder2.video_date = (TextView) view.findViewById(R.id.video_date);
            LinearLayout unused3 = viewHolder2.videolayout = (LinearLayout) view.findViewById(R.id.videolayout);
            TextView unused4 = viewHolder2.video_duration = (TextView) view.findViewById(R.id.video_duration);
            ImageView unused5 = viewHolder2.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (Commons.language.equals("en_US")) {
            viewHolder.video_title.setText(this.item[i].getTitle_e());
        } else {
            viewHolder.video_title.setText(this.item[i].getTitle_c());
        }
        viewHolder.video_duration.setText(this.item[i].getDuration());
        viewHolder.video_date.setText(StringFormatter.formatFullDate(this.item[i].getDate()));
        ImageView access$400 = viewHolder.imageView;
        UrlImageViewHelper.setUrlDrawable(access$400, this.context.getResources().getText(R.string.url_domain).toString() + this.item[i].getImgdata(), (Drawable) null, 86400000);
        final String videourl = this.item[i].getVideourl();
        viewHolder.videolayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(videourl), "video/*");
                    VideoAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
