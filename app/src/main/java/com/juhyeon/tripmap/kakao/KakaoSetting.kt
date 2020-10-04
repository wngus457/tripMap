package com.juhyeon.tripmap.kakao

import android.app.Application
import android.util.Log
import com.juhyeon.tripmap.R
import com.kakao.sdk.common.KakaoSdk

class KakaoSetting : Application() {
    override fun onCreate() {
        super.onCreate()
        //카카오 초기화
        val kakaoKeyNative : String = getString(R.string.kakao_key_native)
        Log.d("KakaoSetting", kakaoKeyNative)
        KakaoSdk.init(this, "{$kakaoKeyNative}")
    }
}