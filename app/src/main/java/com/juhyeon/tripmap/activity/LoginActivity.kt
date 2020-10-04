package com.juhyeon.tripmap.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.juhyeon.tripmap.R
import com.juhyeon.tripmap.kakao.KakaoSetting
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.util.Utility
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        signUpText.setOnClickListener{
            var intent : Intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}