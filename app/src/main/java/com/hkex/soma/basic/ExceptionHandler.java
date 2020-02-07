package com.hkex.soma.basic;

import android.app.Activity;
import android.os.Build;
import android.os.Process;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final String LINE_SEPARATOR = "\n";
    private final Activity myContext;

    public ExceptionHandler(Activity activity) {
        this.myContext = activity;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        StringBuilder sb = new StringBuilder();
        sb.append("************ CAUSE OF ERROR ************\n\n");
        sb.append(stringWriter.toString());
        sb.append("\n************ DEVICE INFORMATION ***********\n");
        sb.append("Brand: ");
        sb.append(Build.BRAND);
        sb.append("\n");
        sb.append("Device: ");
        sb.append(Build.DEVICE);
        sb.append("\n");
        sb.append("Model: ");
        sb.append(Build.MODEL);
        sb.append("\n");
        sb.append("Id: ");
        sb.append(Build.ID);
        sb.append("\n");
        sb.append("Product: ");
        sb.append(Build.PRODUCT);
        sb.append("\n");
        sb.append("\n************ FIRMWARE ************\n");
        sb.append("SDK: ");
        sb.append(Build.VERSION.SDK);
        sb.append("\n");
        sb.append("Release: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("\n");
        sb.append("Incremental: ");
        sb.append(Build.VERSION.INCREMENTAL);
        sb.append("\n");
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}
