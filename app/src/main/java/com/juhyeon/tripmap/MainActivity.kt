package com.juhyeon.tripmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.juhyeon.tripmap.kakao.KakaoSetting
import com.kakao.sdk.auth.LoginClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        KakaoSetting()

        // 카카오계정으로 로그인
        kakaoLoginBtn.setOnClickListener {
            LoginClient.instance.loginWithKakaoAccount(applicationContext) { token, error ->
                if (error != null) {
                    Log.d("MainActivity", "로그인 실패")
                }
                else if (token != null) {
                    Log.d("MainActivity", "로그인 성공")
                }
            }
        }
    }
}