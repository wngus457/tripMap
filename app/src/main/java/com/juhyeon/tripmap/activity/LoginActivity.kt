package com.juhyeon.tripmap.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.juhyeon.tripmap.MainActivity
import com.juhyeon.tripmap.R
import com.juhyeon.tripmap.kakao.KakaoSetting
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.util.Utility
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val GOOGLE_LOGIN_CODE = 9999
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        KakaoSetting()

        googleLoginBtn.setOnClickListener{
            signIn()
        }
        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            //'R.string.default_web_client_id' 에는 본인의 클라이언트 아이디를 넣어주시면 됩니다.
            //저는 스트링을 따로 빼서 저렇게 사용했지만 스트링을 통째로 넣으셔도 됩니다.
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()

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
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_LOGIN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    } // onActivityResult End

    // firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                    toMainActivity(firebaseAuth.currentUser)
                } else {
                    Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                }
            }
    }// firebaseAuthWithGoogle END

    fun toMainActivity(user: FirebaseUser?) {
        if(user !=null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    }

    // google login signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }
}