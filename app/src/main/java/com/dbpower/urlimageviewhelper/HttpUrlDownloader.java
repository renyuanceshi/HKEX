package com.dbpower.urlimageviewhelper;

import android.content.Context;
import android.os.AsyncTask;
import com.dbpower.urlimageviewhelper.UrlDownloader;
import com.dbpower.urlimageviewhelper.UrlImageViewHelper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.NameValuePair;

public class HttpUrlDownloader implements UrlDownloader {
    /* access modifiers changed from: private */
    public UrlImageViewHelper.RequestPropertiesCallback mRequestPropertiesCallback;

    public boolean allowCache() {
        return true;
    }

    public boolean canDownloadUrl(String str) {
        return str.startsWith("http");
    }

    public void download(Context context, String str, String str2, UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, Runnable runnable) {
        final String str3 = str;
        final Context context2 = context;
        final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback2 = urlDownloaderCallback;
        final Runnable runnable2 = runnable;
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                HttpURLConnection httpURLConnection;
                ArrayList<NameValuePair> headersForRequest;
                try {
                    String str = str3;
                    while (true) {
                        httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                        httpURLConnection.setInstanceFollowRedirects(true);
                        if (!(HttpUrlDownloader.this.mRequestPropertiesCallback == null || (headersForRequest = HttpUrlDownloader.this.mRequestPropertiesCallback.getHeadersForRequest(context2, str3)) == null)) {
                            Iterator<NameValuePair> it = headersForRequest.iterator();
                            while (it.hasNext()) {
                                NameValuePair next = it.next();
                                httpURLConnection.addRequestProperty(next.getName(), next.getValue());
                            }
                        }
                        if (httpURLConnection.getResponseCode() != 302 && httpURLConnection.getResponseCode() != 301) {
                            break;
                        }
                        str = httpURLConnection.getHeaderField("Location");
                    }
                    if (httpURLConnection.getResponseCode() != 200) {
                        UrlImageViewHelper.clog("Response Code: " + httpURLConnection.getResponseCode(), new Object[0]);
                    } else {
                        urlDownloaderCallback2.onDownloadComplete(HttpUrlDownloader.this, httpURLConnection.getInputStream(), (String) null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                return null;
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Void voidR) {
                runnable2.run();
            }
        });
    }

    public UrlImageViewHelper.RequestPropertiesCallback getRequestPropertiesCallback() {
        return this.mRequestPropertiesCallback;
    }

    public void setRequestPropertiesCallback(UrlImageViewHelper.RequestPropertiesCallback requestPropertiesCallback) {
        this.mRequestPropertiesCallback = requestPropertiesCallback;
    }
}
