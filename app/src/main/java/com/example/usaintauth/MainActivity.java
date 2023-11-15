/*
2023-11-15
Activity: usaintLogin

주의사항
1. WebView type 변수 class field에 선언 불가능
2. WebView visibility VISIBLE일 때만 getHtml 가능
 */


package com.example.usaintauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

class UsaintLoginWebPage {

    @SuppressLint("SetJavaScriptEnabled")
    void setLoginPage(@NonNull WebView webView) {
        webView.getSettings().setLoadWithOverviewMode(true);                                        // html content를 WebView 크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야 함
        webView.getSettings().setUseWideViewPort(true);                                             // setLoadWithOverviewMode 와 같이 써야 함
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);                                        // 줌 확대/축소 버튼 여부
        webView.getSettings().setSupportMultipleWindows(true);                                      // 멀티 윈도우 사용 여부
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);                       // javascript가 window.open()을 사용할 수 있도록 설정
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (isLoginSuccessful(url)) {
                    Log.d("park", "login success!");
                    view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);");     // MyJavaScriptInterface의 getHtml() 호출
                }

                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    boolean isLoginSuccessful(@NonNull String url) {
        return url.equals("https://saint.ssu.ac.kr/irj/portal");
    }

    void loadLoginPage(@NonNull WebView webView) {
        Log.d("park", "start loading loginPage...");
        webView.loadUrl("https://smartid.ssu.ac.kr/Symtra_sso/smln.asp?apiReturnUrl=https%3A%2F%2Fsaint.ssu.ac.kr%2FwebSSO%2Fsso.jsp");
    }

    static class MyJavaScriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            Document doc = Jsoup.parse(html);
//            Log.d("park", "----------------------------------\n" + doc);

            for (Element e : doc.select(".main_title")) {
                Log.d("park", "StudentName: " + e.text());
            }

            String studentName = doc.select(".main_title").text();
            Log.d("park", "StudentName:" + studentName);

//            String studentId = doc.select("main_box09_con").select("a").first().text();
//            String studentMajor = doc.select("ul[class=main_box09_con] li dl dd a").next().text();
//            Log.d("park", "studentId: " + studentId);
//            Log.d("park", "StudentMajor: " + studentMajor);
        }
    }
}