package com.example.test.mvvmsampleapp.core.multilanguage;

/**
 * Created by Javad Vatan on 4/4/2018.
 */


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleManager {

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_PERSIAN = "fa";
    public static final String LANGUAGE_TURKISH = "tr";

    public static Context setLocale(Context mContext) {
        return mContext /*updateResources(mContext, Setting.getUserLocal())*/;
    }

   /* public static void setNewLocale(Context mContext, String language, boolean restartProcess) {
        LocaleManager.setNewLocale(mContext, language);
        RxBus.INSTANCE.publish(new RxEvent.GeneralEvent(Constants.BROAD_CAST_CHANGE_LANGUAGE, null));

        if (restartProcess) {
            GlobalFunction.getInstance().rebootApp(mContext);
        }
    }

    public static Context setNewLocale(Context c, String language) {
        Setting.setUserLocal(language);
        return updateResources(c, language);
    }
*/
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

  /*  public static String getString(Context mContext, int id) {
        Configuration conf = mContext.getResources().getConfiguration();
        conf.locale = new Locale(Setting.getUserLocal());
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        Resources resources = new Resources(mContext.getAssets(), metrics, conf);
        return resources.getString(id);
    }*/
}