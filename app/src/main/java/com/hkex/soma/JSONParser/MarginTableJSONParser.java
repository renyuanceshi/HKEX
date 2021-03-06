package com.hkex.soma.JSONParser;

import android.util.Log;
import com.hkex.soma.dataModel.MarginTable_Result;
import com.hkex.soma.utils.Commons;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class MarginTableJSONParser {
    /* access modifiers changed from: private */
    public OnJSONCompletedListener onJSONCompletedListener;
    /* access modifiers changed from: private */
    public OnJSONFailedListener onJSONFailedListener;
    private Executor singleTp = Executors.newSingleThreadExecutor();

    public interface OnJSONCompletedListener {
        void OnJSONCompleted(MarginTable_Result marginTable_Result);
    }

    public interface OnJSONFailedListener {
        void OnJSONFailed(Runnable runnable, Exception exc);
    }

    public void loadXML(final String str) {
        this.singleTp.execute(new Runnable() {
            public void run() {
                Log.v("loadXML", "url " + str);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                try {
                    BasicHttpParams basicHttpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(basicHttpParams, Commons.timeoutConnection);
                    HttpConnectionParams.setSoTimeout(basicHttpParams, Commons.timeoutSocket);
                    HttpResponse execute = new DefaultHttpClient(basicHttpParams).execute(Commons.getHttpRequest(str));
                    InputStream content = new BufferedHttpEntity(execute.getEntity()).getContent();
                    Header firstHeader = execute.getFirstHeader("Content-Encoding");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("gzip")) ? content : new GZIPInputStream(content)));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine + "\n");
                        } else {
                            MarginTableJSONParser.this.onJSONCompletedListener.OnJSONCompleted((MarginTable_Result) objectMapper.readValue(sb.toString(), MarginTable_Result.class));
                            return;
                        }
                    }
                } catch (Exception e) {
                    MarginTableJSONParser.this.onJSONFailedListener.OnJSONFailed(this, e);
                }
            }
        });
    }

    public void setOnJSONCompletedListener(OnJSONCompletedListener onJSONCompletedListener2) {
        this.onJSONCompletedListener = onJSONCompletedListener2;
    }

    public void setOnJSONFailedListener(OnJSONFailedListener onJSONFailedListener2) {
        this.onJSONFailedListener = onJSONFailedListener2;
    }
}
