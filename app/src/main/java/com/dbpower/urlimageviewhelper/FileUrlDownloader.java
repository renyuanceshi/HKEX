package com.dbpower.urlimageviewhelper;

import android.content.Context;
import android.os.AsyncTask;
import com.dbpower.urlimageviewhelper.UrlDownloader;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

public class FileUrlDownloader implements UrlDownloader {
    public boolean allowCache() {
        return false;
    }

    public boolean canDownloadUrl(String str) {
        return str.startsWith("file:/");
    }

    public void download(Context context, final String str, String str2, final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, final Runnable runnable) {
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                try {
                    urlDownloaderCallback.onDownloadComplete(FileUrlDownloader.this, (InputStream) null, new File(new URI(str)).getAbsolutePath());
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                return null;
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Void voidR) {
                runnable.run();
            }
        });
    }
}
