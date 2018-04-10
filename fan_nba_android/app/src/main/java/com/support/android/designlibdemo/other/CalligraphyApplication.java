package com.support.android.designlibdemo.other;

import android.app.Application;

import com.support.android.designlibdemo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by LearnerMN on 1/13/16.
 */
public class CalligraphyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}