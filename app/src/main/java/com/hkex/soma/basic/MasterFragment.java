package com.hkex.soma.basic;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.hkex.soma.utils.Commons;
import java.util.Locale;

public class MasterFragment extends Fragment {
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        String locale = getResources().getConfiguration().locale.toString();
        Log.v("current", "MasterFragment onCreate " + locale + " : " + Commons.language);
        if (Commons.language != null && !locale.equals(Commons.language)) {
            String[] split = Commons.language.split("_");
            Locale locale2 = new Locale(split[0], split[1]);
            Locale.setDefault(locale2);
            Configuration configuration = new Configuration();
            configuration.locale = locale2;
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }

    public void onResume() {
        super.onResume();
    }
}
