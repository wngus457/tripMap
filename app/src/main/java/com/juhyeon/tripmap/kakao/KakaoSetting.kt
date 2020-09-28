package com.juhyeon.tripmap.kakao

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoSetting : Application() {
    override fun onCreate() {
        super.onCreate()
        //카카오 초기화
        KakaoSdk.init(this, "{abbda72ba7365773959afeeeff4c46bb}")
    }
}