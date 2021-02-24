package com.commercejunction.constants

import `in`.myinnos.customfontlibrary.TypefaceUtil
import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/source_sans_pro.regular.ttf");
    }
}