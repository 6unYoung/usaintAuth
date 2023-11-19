/*
2023-11-15
Activity: usaintLogin

주의사항
1. WebView type 변수 class field에 선언 불가능
2. WebView visibility VISIBLE일 때만 getHtml 가능
 */


package com.example.usaintauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView wv = findViewById(R.id.usaint_login_wv);
        UsaintLoginWebPage loginWebPage = new UsaintLoginWebPage();
        loginWebPage.setLoginPage(wv);
        loginWebPage.loadLoginPage(wv);
    }
}