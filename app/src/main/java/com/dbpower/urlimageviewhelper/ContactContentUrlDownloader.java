package com.dbpower.urlimageviewhelper;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import com.dbpower.urlimageviewhelper.UrlDownloader;

public class ContactContentUrlDownloader implements UrlDownloader {
    public boolean allowCache() {
        return false;
    }

    public boolean canDownloadUrl(String str) {
        return str.startsWith(ContactsContract.Contacts.CONTENT_URI.toString());
    }

    public void download(Context context, String str, String str2, UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, Runnable runnable) {
        final Context context2 = context;
        final String str3 = str;
        final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback2 = urlDownloaderCallback;
        final Runnable runnable2 = runnable;
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                try {
                    urlDownloaderCallback2.onDownloadComplete(ContactContentUrlDownloader.this, ContactsContract.Contacts.openContactPhotoInputStream(context2.getContentResolver(), Uri.parse(str3)), (String) null);
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
}
