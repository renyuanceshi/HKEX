package com.hkex.soma.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hkex.soma.slideAnimator.AnimatedFragmentActivity;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoaderImageView extends LinearLayout {
    private static final int COMPLETE = 0;
    private static final int FAILED = 1;
    private int height = -1;
    private final Handler imageLoadedHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return true;
            }
            LoaderImageView.this.mImage.setImageDrawable(LoaderImageView.this.mDrawable);
            LoaderImageView.this.mImage.setVisibility(View.VISIBLE);
            LoaderImageView.this.mSpinner.setVisibility(View.GONE);
            return true;
        }
    });
    private int loadingimg;
    private Context mContext;
    /* access modifiers changed from: private */
    public Drawable mDrawable;
    /* access modifiers changed from: private */
    public ImageView mImage;
    /* access modifiers changed from: private */
    public ProgressBar mSpinner;

    public LoaderImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.height = Math.round(Float.parseFloat(attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height").replace("dip", "")));
        String attributeValue = attributeSet.getAttributeValue((String) null, "image");
        if (attributeValue != null) {
            instantiate(context, attributeValue);
        } else {
            instantiate(context, (String) null);
        }
    }

    public LoaderImageView(Context context, String str) {
        super(context);
        instantiate(context, str);
    }

    /* access modifiers changed from: private */
    public static Drawable getDrawableFromUrl(String str) throws IOException, MalformedURLException {
        return Drawable.createFromStream((InputStream) new URL(str).getContent(), "name");
    }

    private void instantiate(Context context, String str) {
        this.mContext = context;
        this.mImage = new ImageView(this.mContext);
        Log.v("", "height " + this.height + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        this.mImage.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.mSpinner = new ProgressBar(this.mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        this.mSpinner.setLayoutParams(layoutParams);
        this.mSpinner.setIndeterminate(true);
        addView(this.mSpinner);
        addView(this.mImage);
        if (str != null) {
            setImageDrawable(str);
        }
    }

    /* access modifiers changed from: private */
    public void waitOrShowImage() {
        if (this.mContext instanceof AnimatedFragmentActivity) {
            if (!((AnimatedFragmentActivity) this.mContext).leftViewOut && !((AnimatedFragmentActivity) this.mContext).rightViewOut) {
                this.imageLoadedHandler.sendEmptyMessage(0);
                return;
            }
            try {
                Thread.sleep(1000);
                waitOrShowImage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            this.imageLoadedHandler.sendEmptyMessage(0);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, ImageView.ScaleType.FIT_CENTER);
    }

    public void setImageBitmap(Bitmap bitmap, ImageView.ScaleType scaleType) {
        this.mDrawable = null;
        this.mSpinner.setVisibility(View.VISIBLE);
        this.mImage.setVisibility(View.GONE);
        this.mImage.setScaleType(scaleType);
        this.mImage.setImageBitmap(bitmap);
        this.mImage.setVisibility(View.VISIBLE);
        this.mSpinner.setVisibility(View.GONE);
    }

    public void setImageDrawable(String str) {
        setImageDrawable(str, ImageView.ScaleType.FIT_CENTER);
    }

    public void setImageDrawable(final String str, ImageView.ScaleType scaleType) {
        this.mDrawable = null;
        this.mSpinner.setVisibility(View.VISIBLE);
        this.mImage.setVisibility(View.GONE);
        this.mImage.setScaleType(scaleType);
        new Thread() {
            public void run() {
                try {
                    Drawable unused = LoaderImageView.this.mDrawable = LoaderImageView.getDrawableFromUrl(str);
                    LoaderImageView.this.waitOrShowImage();
                } catch (MalformedURLException e) {
                    LoaderImageView.this.imageLoadedHandler.sendEmptyMessage(1);
                } catch (IOException e2) {
                    LoaderImageView.this.imageLoadedHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public void setLoading(int i) {
        this.loadingimg = i;
        this.mSpinner.setVisibility(View.GONE);
        this.mImage.setVisibility(View.VISIBLE);
        this.mImage.setImageResource(this.loadingimg);
    }

    public void showLoading() {
        this.mSpinner.setVisibility(View.GONE);
        this.mImage.setVisibility(View.VISIBLE);
        this.mImage.setImageResource(this.loadingimg);
    }
}
