package com.dbpower.urlimageviewhelper;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

//import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

public final class UrlImageViewHelper {
   public static final int CACHE_DURATION_FIVE_DAYS = 432000000;
   public static final int CACHE_DURATION_FOUR_DAYS = 345600000;
   public static final int CACHE_DURATION_INFINITE = Integer.MAX_VALUE;
   public static final int CACHE_DURATION_ONE_DAY = 86400000;
   public static final int CACHE_DURATION_ONE_WEEK = 604800000;
   public static final int CACHE_DURATION_SIX_DAYS = 518400000;
   public static final int CACHE_DURATION_THREE_DAYS = 259200000;
   public static final int CACHE_DURATION_TWO_DAYS = 172800000;
   private static HashSet mAllCache;
   private static ContactContentUrlDownloader mContactDownloader = new ContactContentUrlDownloader();
   private static ContentUrlDownloader mContentDownloader = new ContentUrlDownloader();
   private static LruBitmapCache mDeadCache;
   private static ArrayList mDownloaders = new ArrayList();
   private static FileUrlDownloader mFileDownloader = new FileUrlDownloader();
   private static boolean mHasCleaned;
   private static HttpUrlDownloader mHttpDownloader = new HttpUrlDownloader();
   private static DrawableCache mLiveCache;
   static DisplayMetrics mMetrics;
   private static Hashtable mPendingDownloads;
   private static Hashtable mPendingViews;
   private static UrlImageViewHelper.RequestPropertiesCallback mRequestPropertiesCallback;
   static Resources mResources;
   private static boolean mUseBitmapScaling;

   static {
      mDownloaders.add(mHttpDownloader);
      mDownloaders.add(mContactDownloader);
      mDownloaders.add(mContentDownloader);
      mDownloaders.add(mFileDownloader);
      mLiveCache = DrawableCache.getInstance();
      mAllCache = new HashSet();
      mPendingViews = new Hashtable();
      mPendingDownloads = new Hashtable();
   }

   // $FF: synthetic method
   static Bitmap access$3(Context var0, String var1, String var2, int var3, int var4) {
      return loadBitmapFromStream(var0, var1, var2, var3, var4);
   }

   private static boolean checkCacheDuration(File var0, long var1) {
      return var1 == 2147483647L || System.currentTimeMillis() < var0.lastModified() + var1;
   }

   public static void cleanup(Context var0) {
      cleanup(var0, 604800000L);
   }

   public static void cleanup(Context var0, long var1) {
      if (!mHasCleaned) {
         mHasCleaned = true;

         Exception var10000;
         label62: {
            String[] var3;
            boolean var10001;
            try {
               var3 = var0.getFilesDir().list();
            } catch (Exception var12) {
               var10000 = var12;
               var10001 = false;
               break label62;
            }

            if (var3 == null) {
               return;
            }

            int var4;
            try {
               var4 = var3.length;
            } catch (Exception var11) {
               var10000 = var11;
               var10001 = false;
               break label62;
            }

            int var5 = 0;

            while(true) {
               if (var5 >= var4) {
                  return;
               }

               String var6 = var3[var5];

               label43: {
                  try {
                     if (!var6.endsWith(".urlimage")) {
                        break label43;
                     }
                  } catch (Exception var10) {
                     var10000 = var10;
                     var10001 = false;
                     break;
                  }

                  try {
                     StringBuilder var8 = new StringBuilder(String.valueOf(var0.getFilesDir().getAbsolutePath()));
                     var8.append('/');
                     var8.append(var6);
                     File var7 = new File(var8.toString());
                     if (System.currentTimeMillis() > var7.lastModified() + 604800000L) {
                        var7.delete();
                     }
                  } catch (Exception var9) {
                     var10000 = var9;
                     var10001 = false;
                     break;
                  }
               }

               ++var5;
            }
         }

         Exception var13 = var10000;
         var13.printStackTrace();
      }
   }

   static void clog(String var0, Object... var1) {
      if (var1.length != 0) {
         String.format(var0, var1);
      }

   }

   public static int copyStream(InputStream var0, OutputStream var1) throws IOException {
      byte[] var2 = new byte[1024];
      int var3 = 0;

      while(true) {
         int var4 = var0.read(var2);
         if (var4 == -1) {
            return var3;
         }

         var1.write(var2, 0, var4);
         var3 += var4;
      }
   }

   static void executeTask(AsyncTask var0) {
      if (VERSION.SDK_INT < 11) {
         var0.execute(new Void[0]);
      } else {
         executeTaskHoneycomb(var0);
      }

   }

   @TargetApi(11)
   private static void executeTaskHoneycomb(AsyncTask var0) {
      var0.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
   }

   public static Bitmap getCachedBitmap(String var0) {
      if (var0 == null) {
         return null;
      } else {
         Bitmap var1;
         if (mDeadCache != null) {
            var1 = (Bitmap)mDeadCache.get(var0);
         } else {
            var1 = null;
         }

         if (var1 != null) {
            return var1;
         } else {
            if (mLiveCache != null) {
               Drawable var2 = (Drawable)mLiveCache.get(var0);
               if (var2 instanceof UrlImageViewHelper.ZombieDrawable) {
                  return ((UrlImageViewHelper.ZombieDrawable)var2).getBitmap();
               }
            }

            return null;
         }
      }
   }

   public static ArrayList getDownloaders() {
      return mDownloaders;
   }

   public static String getFilenameForUrl(String var0) {
      StringBuilder var1 = new StringBuilder(String.valueOf(var0.hashCode()));
      var1.append(".urlimage");
      return var1.toString();
   }

   private static int getHeapSize(Context var0) {
      return ((ActivityManager)var0.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() * 1024 * 1024;
   }

   public static UrlImageViewHelper.RequestPropertiesCallback getRequestPropertiesCallback() {
      return mRequestPropertiesCallback;
   }

   public static boolean getUseBitmapScaling() {
      return mUseBitmapScaling;
   }

   private static boolean isNullOrEmpty(CharSequence var0) {
      return var0 == null || var0.equals("") || var0.equals("null") || var0.equals("NULL");
   }

   private static Bitmap loadBitmapFromStream(Context param0, String param1, String param2, int param3, int param4) {
      // $FF: Couldn't be decompiled
      //!LC
      return null;
   }

   public static void loadUrlDrawable(Context var0, String var1) {
      setUrlDrawable(var0, (ImageView)null, var1, (Drawable)null, 259200000L, (UrlImageViewCallback)null);
   }

   public static void loadUrlDrawable(Context var0, String var1, long var2) {
      setUrlDrawable(var0, (ImageView)null, var1, (Drawable)null, var2, (UrlImageViewCallback)null);
   }

   public static void loadUrlDrawable(Context var0, String var1, long var2, UrlImageViewCallback var4) {
      setUrlDrawable(var0, (ImageView)null, var1, (Drawable)null, var2, var4);
   }

   public static void loadUrlDrawable(Context var0, String var1, UrlImageViewCallback var2) {
      setUrlDrawable(var0, (ImageView)null, var1, (Drawable)null, 259200000L, var2);
   }

   private static void prepareResources(Context var0) {
      if (mMetrics == null) {
         mMetrics = new DisplayMetrics();
         ((WindowManager)var0.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(mMetrics);
         mResources = new Resources(var0.getAssets(), mMetrics, var0.getResources().getConfiguration());
      }
   }

   public static void setRequestPropertiesCallback(UrlImageViewHelper.RequestPropertiesCallback var0) {
      mRequestPropertiesCallback = var0;
   }

   private static void setUrlDrawable(Context var0, ImageView var1, String var2, int var3, long var4) {
      Drawable var6;
      if (var3 != 0) {
         var6 = var1.getResources().getDrawable(var3);
      } else {
         var6 = null;
      }

      setUrlDrawable(var0, var1, var2, var6, var4, (UrlImageViewCallback)null);
   }

   private static void setUrlDrawable(Context var0, ImageView var1, String var2, int var3, long var4, UrlImageViewCallback var6) {
      Drawable var7;
      if (var3 != 0) {
         var7 = var1.getResources().getDrawable(var3);
      } else {
         var7 = null;
      }

      setUrlDrawable(var0, var1, var2, var7, var4, var6);
   }

   private static void setUrlDrawable(final Context var0, final ImageView var1, final String var2, final Drawable var3, long var4, final UrlImageViewCallback var6) {
      boolean var7;
      if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
         var7 = true;
      } else {
         var7 = false;
      }

//      Assert.assertTrue("setUrlDrawable and loadUrlDrawable should only be called from the main thread.", var7);
      cleanup(var0);
      if (isNullOrEmpty(var2)) {
         if (var1 != null) {
            mPendingViews.remove(var1);
            var1.setImageDrawable(var3);
         }

      } else {
         if (mMetrics == null) {
            prepareResources(var0);
         }

         int var8 = mMetrics.widthPixels;
         int var9 = mMetrics.heightPixels;
         final String var10 = var0.getFileStreamPath(getFilenameForUrl(var2)).getAbsolutePath();
         File var11 = new File(var10);
         if (mDeadCache == null) {
            mDeadCache = new LruBitmapCache(getHeapSize(var0) / 8);
         }

         Bitmap var12 = (Bitmap)mDeadCache.remove(var2);
         StringBuilder var13;
         Object var14;
         if (var12 != null) {
            var13 = new StringBuilder("zombie load: ");
            var13.append(var2);
            clog(var13.toString());
            var14 = new UrlImageViewHelper.ZombieDrawable(var2, mResources, var12);
         } else {
            var14 = (Drawable)mLiveCache.get(var2);
         }

         Object var21 = var14;
         if (var14 != null) {
            var13 = new StringBuilder("Cache hit on: ");
            var13.append(var2);
            clog(var13.toString());
            if (var11.exists() && !checkCacheDuration(var11, var4)) {
               var13 = new StringBuilder("Cache hit, but file is stale. Forcing reload: ");
               var13.append(var2);
               clog(var13.toString());
               if (var14 instanceof UrlImageViewHelper.ZombieDrawable) {
                  ((UrlImageViewHelper.ZombieDrawable)var14).headshot();
               }

               var21 = null;
            } else {
               var13 = new StringBuilder("Using cached: ");
               var13.append(var2);
               clog(var13.toString());
               var21 = var14;
            }
         }

         if (var21 != null) {
            Object var18 = var21;
            if (var1 != null) {
               mPendingViews.remove(var1);
               var18 = var21;
               if (var21 instanceof UrlImageViewHelper.ZombieDrawable) {
                  var18 = ((UrlImageViewHelper.ZombieDrawable)var21).clone(mResources);
               }

               var1.setImageDrawable((Drawable)var18);
            }

            if (var6 != null) {
               var7 = var18 instanceof UrlImageViewHelper.ZombieDrawable;
               var6.onLoaded(var1, var12, var2, true);
            }

         } else {
            var13 = new StringBuilder("Waiting for ");
            var13.append(var2);
            var13.append(" ");
            var13.append(var1);
            clog(var13.toString());
            if (var1 != null) {
               var1.setImageDrawable(var3);
               mPendingViews.put(var1, var2);
            }

            ArrayList var25 = (ArrayList)mPendingDownloads.get(var2);
            if (var25 != null) {
               if (var1 != null) {
                  var25.add(var1);
               }

            } else {
               final ArrayList var22 = new ArrayList();
               if (var1 != null) {
                  var22.add(var1);
               }

               mPendingDownloads.put(var2, var22);
               if (var8 <= 0) {
                  var8 = Integer.MAX_VALUE;
               }

               if (var9 <= 0) {
                  var9 = Integer.MAX_VALUE;
               }

               final UrlImageViewHelper.Loader var27 = new UrlImageViewHelper.Loader() {
                  public void onDownloadComplete(UrlDownloader param1, InputStream param2, String param3) {
                     // $FF: Couldn't be decompiled
                  }
               };
               Runnable var19 = new Runnable() {
                  public void run() {
//                     Assert.assertEquals(Looper.myLooper(), Looper.getMainLooper());
                     Bitmap var1x = var27.result;
                     UrlImageViewHelper.ZombieDrawable var8;
                     if (var1x != null) {
                        var8 = new UrlImageViewHelper.ZombieDrawable(var2, UrlImageViewHelper.mResources, var1x);
                     } else {
                        var8 = null;
                     }

                     Drawable var2x = var8;
                     StringBuilder var9;
                     if (var8 == null) {
                        var9 = new StringBuilder("No usable result, defaulting ");
                        var9.append(var2);
                        UrlImageViewHelper.clog(var9.toString());
                        var2x = var3;
                        UrlImageViewHelper.mLiveCache.put(var2, var2x);
                     }

                     UrlImageViewHelper.mPendingDownloads.remove(var2);
                     if (var6 != null && var1 == null) {
                        var6.onLoaded((ImageView)null, var27.result, var2, false);
                     }

                     Iterator var3x = var22.iterator();
                     int var4 = 0;

                     while(var3x.hasNext()) {
                        ImageView var5 = (ImageView)var3x.next();
                        String var6x = (String)UrlImageViewHelper.mPendingViews.get(var5);
                        if (!var2.equals(var6x)) {
                           var9 = new StringBuilder("Ignoring out of date request to update view for ");
                           var9.append(var2);
                           var9.append(" ");
                           var9.append(var6x);
                           var9.append(" ");
                           var9.append(var5);
                           UrlImageViewHelper.clog(var9.toString());
                        } else {
                           int var7 = var4 + 1;
                           UrlImageViewHelper.mPendingViews.remove(var5);
                           if (var2x != null) {
                              var5.setImageDrawable((Drawable)var2x);
                           }

                           var4 = var7;
                           if (var6 != null) {
                              var4 = var7;
                              if (var5 == var1) {
                                 var6.onLoaded(var5, var27.result, var2, false);
                                 var4 = var7;
                              }
                           }
                        }
                     }

                     var9 = new StringBuilder("Populated: ");
                     var9.append(var4);
                     UrlImageViewHelper.clog(var9.toString());
                  }
               };
               if (var11.exists()) {
                  label103: {
                     boolean var10001;
                     AsyncTask var24;
                     label102: {
                        try {
                           if (checkCacheDuration(var11, var4)) {
                              StringBuilder var23 = new StringBuilder("File Cache hit on: ");
                              var23.append(var2);
                              var23.append(". ");
                              var23.append(System.currentTimeMillis() - var11.lastModified());
                              var23.append("ms old.");
                              clog(var23.toString());
//                              var24 = new AsyncTask() {
////                                 // $FF: synthetic field
////                                 private final Runnable val$completion;
////                                 // $FF: synthetic field
////                                 private final String val$filename;
//
//                                 {
////!LC
////                                    this.val$filename = var2;
////                                    this.val$completion = var3;
//                                 }
//
//                                 protected Void doInBackground(Void... var1) {
//                                    //!LC UrlImageViewHelper.this.onDownloadComplete((UrlDownloader)null, (InputStream)null, this.val$filename);
//                                    return null;
//                                 }
//
//                                 protected void onPostExecute(Void var1) {
////                                    this.val$completion.run();
//                                 }
//                              };
                              break label102;
                           }
                        } catch (Exception var17) {
                           break label103;
                        }

                        try {
                           clog("File cache has expired. Refreshing.");
                        } catch (Exception var16) {
                           var10001 = false;
                        }
                        break label103;
                     }

                     try {
// !LC
//                        var24.<init>(var10, var19);
//                        executeTask(var24);
                        return;
                     } catch (Exception var15) {
                        var10001 = false;
                     }
                  }
               }

               Iterator var26 = mDownloaders.iterator();

               while(var26.hasNext()) {
                  UrlDownloader var20 = (UrlDownloader)var26.next();
                  if (var20.canDownloadUrl(var2)) {
                     var20.download(var0, var2, var10, var27, var19);
                     return;
                  }
               }

               var1.setImageDrawable(var3);
            }
         }
      }
   }

   public static void setUrlDrawable(ImageView var0, String var1) {
      setUrlDrawable(var0.getContext(), var0, var1, (Drawable)null, 259200000L, (UrlImageViewCallback)null);
   }

   public static void setUrlDrawable(ImageView var0, String var1, int var2) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, 259200000L);
   }

   public static void setUrlDrawable(ImageView var0, String var1, int var2, long var3) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, var3);
   }

   public static void setUrlDrawable(ImageView var0, String var1, int var2, long var3, UrlImageViewCallback var5) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, var3, var5);
   }

   public static void setUrlDrawable(ImageView var0, String var1, int var2, UrlImageViewCallback var3) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, 259200000L, var3);
   }

   public static void setUrlDrawable(ImageView var0, String var1, Drawable var2) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, 259200000L, (UrlImageViewCallback)null);
   }

   public static void setUrlDrawable(ImageView var0, String var1, Drawable var2, long var3) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, var3, (UrlImageViewCallback)null);
   }

   public static void setUrlDrawable(ImageView var0, String var1, Drawable var2, long var3, UrlImageViewCallback var5) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, var3, var5);
   }

   public static void setUrlDrawable(ImageView var0, String var1, Drawable var2, UrlImageViewCallback var3) {
      setUrlDrawable(var0.getContext(), var0, var1, var2, 259200000L, var3);
   }

   public static void setUrlDrawable(ImageView var0, String var1, UrlImageViewCallback var2) {
      setUrlDrawable(var0.getContext(), var0, var1, (Drawable)null, 259200000L, var2);
   }

   public static void setUseBitmapScaling(boolean var0) {
      mUseBitmapScaling = var0;
   }

   private abstract static class Loader implements UrlDownloader.UrlDownloaderCallback {
      Bitmap result;

      private Loader() {
      }

      // $FF: synthetic method
      Loader(UrlImageViewHelper.Loader var1) {
         this();
      }
   }

   public interface RequestPropertiesCallback {
      ArrayList getHeadersForRequest(Context var1, String var2);
   }

   private static class ZombieDrawable extends BitmapDrawable {
      UrlImageViewHelper.ZombieDrawable.Brains mBrains;
      String mUrl;

      public ZombieDrawable(String var1, Resources var2, Bitmap var3) {
         this(var1, var2, var3, new UrlImageViewHelper.ZombieDrawable.Brains((UrlImageViewHelper.ZombieDrawable.Brains)null));
      }

      private ZombieDrawable(String var1, Resources var2, Bitmap var3, UrlImageViewHelper.ZombieDrawable.Brains var4) {
         super(var2, var3);
         this.mUrl = var1;
         this.mBrains = var4;
         UrlImageViewHelper.mAllCache.add(var3);
         UrlImageViewHelper.mDeadCache.remove(var1);
         UrlImageViewHelper.mLiveCache.put(var1, this);
         UrlImageViewHelper.ZombieDrawable.Brains var5 = this.mBrains;
         ++var5.mRefCounter;
      }

      public UrlImageViewHelper.ZombieDrawable clone(Resources var1) {
         return new UrlImageViewHelper.ZombieDrawable(this.mUrl, var1, this.getBitmap(), this.mBrains);
      }

      protected void finalize() throws Throwable {
         super.finalize();
         UrlImageViewHelper.ZombieDrawable.Brains var1 = this.mBrains;
         --var1.mRefCounter;
         if (this.mBrains.mRefCounter == 0) {
            if (!this.mBrains.mHeadshot) {
               UrlImageViewHelper.mDeadCache.put(this.mUrl, this.getBitmap());
            }

            UrlImageViewHelper.mAllCache.remove(this.getBitmap());
            UrlImageViewHelper.mLiveCache.remove(this.mUrl);
            StringBuilder var2 = new StringBuilder("Zombie GC event ");
            var2.append(this.mUrl);
            UrlImageViewHelper.clog(var2.toString());
         }

      }

      public void headshot() {
         StringBuilder var1 = new StringBuilder("BOOM! Headshot: ");
         var1.append(this.mUrl);
         UrlImageViewHelper.clog(var1.toString());
         this.mBrains.mHeadshot = true;
         UrlImageViewHelper.mLiveCache.remove(this.mUrl);
         UrlImageViewHelper.mAllCache.remove(this.getBitmap());
      }

      private static class Brains {
         boolean mHeadshot;
         int mRefCounter;

         private Brains() {
         }

         // $FF: synthetic method
         Brains(UrlImageViewHelper.ZombieDrawable.Brains var1) {
            this();
         }
      }
   }
}
