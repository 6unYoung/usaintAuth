package com.example.usaintauth;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UsaintLoginWebPage {

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

//            String studentName = doc.select("span.top_user").first().text();
//            Log.d("park", "StudentName:" + studentName);

            for (Element e : doc.select(".strong")) {
                Log.d("park", "studentId: " + e.text());
            }
            String studentId = doc.select(".strong").text();
            Log.d("park", "진짜 studentId: " + studentId);
        }
    }
}
