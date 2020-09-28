package com.juhyeon.tripmap.kakao

import android.app.Application
import android.content.res.Resources
import com.juhyeon.tripmap.R
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "{abbda72ba7365773959afeeeff4c46bb}")
    }
}