package com.hkex.soma.element;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkex.soma.R;
import com.hkex.soma.activity.WebViewPage;

public class AppFooter extends LinearLayout {
    private View appfooter;

    public AppFooter(Context context) {
        super(context);
    }

    public AppFooter(final Context context, boolean z) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (z) {
            this.appfooter = layoutInflater.inflate(R.layout.portfooter, this, false);
        } else {
            this.appfooter = layoutInflater.inflate(R.layout.appfooter, this, false);
        }
        addView(this.appfooter);
        ((TextView) this.appfooter.findViewById(R.id.TextView01)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!(context.getClass() == WebViewPage.class && ((WebViewPage) context).ptitle.equals(context.getString(R.string.title_disclaimer)))) {
                    Intent intent = new Intent().setClass(context, WebViewPage.class);
                    intent.putExtra("title", context.getString(R.string.title_disclaimer));
                    intent.putExtra("url", context.getString(R.string.webview_disclaimer));
                    context.startActivity(intent);
                }
            }
        });
    }

    public void addImportantNoteClickListener(final Context context) {
        ((TextView) this.appfooter.findViewById(R.id.tv_disclaimer)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(context, WebViewPage.class);
                intent.putExtra("title", context.getString(R.string.important_note));
                intent.putExtra("url", context.getString(R.string.webview_importantNote));
                context.startActivity(intent);
            }
        });
    }

    public void addIndicesDisclaimerClickListener(final Context context) {
        ((TextView) this.appfooter.findViewById(R.id.tv_disclaimer)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent().setClass(context, WebViewPage.class);
                intent.putExtra("title", context.getString(R.string.indices_disclaimer));
                intent.putExtra("url", context.getString(R.string.webview_disclaimerOnIndices));
                context.startActivity(intent);
            }
        });
    }
}
