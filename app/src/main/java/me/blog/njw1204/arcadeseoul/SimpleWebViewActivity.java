package me.blog.njw1204.arcadeseoul;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SimpleWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        WebView webview = findViewById(R.id.webview_of_simple_activity);
        WebSettings webSettings = webview.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        if (Build.VERSION.SDK_INT >= 21)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {
                    case ERROR_AUTHENTICATION: // 서버에서 사용자 인증 실패
                    case ERROR_BAD_URL: // 잘못된 URL
                    case ERROR_CONNECT: // 서버로 연결 실패
                    case ERROR_FAILED_SSL_HANDSHAKE: // SSL handshake 수행 실패
                    case ERROR_FILE: // 일반 파일 오류
                    case ERROR_FILE_NOT_FOUND: // 파일을 찾을 수 없습니다
                    case ERROR_HOST_LOOKUP: // 서버 또는 프록시 호스트 이름 조회 실패
                    case ERROR_IO: // 서버에서 읽거나 서버로 쓰기 실패
                    case ERROR_PROXY_AUTHENTICATION: // 프록시에서 사용자 인증 실패
                    case ERROR_REDIRECT_LOOP: // 너무 많은 리디렉션
                    case ERROR_TIMEOUT: // 연결 시간 초과
                    case ERROR_TOO_MANY_REQUESTS: // 페이지 로드중 너무 많은 요청 발생
                    case ERROR_UNKNOWN: // 일반 오류
                    case ERROR_UNSUPPORTED_AUTH_SCHEME: // 지원되지 않는 인증 체계
                    case ERROR_UNSUPPORTED_SCHEME:
                        view.loadUrl("about:blank"); // 빈페이지 출력
                        AlertDialog.Builder builder = new AlertDialog.Builder(SimpleWebViewActivity.this);
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setTitle("오류");
                        builder.setMessage("네트워크 상태가 원활하지 않습니다.\n다시 시도해주세요.");
                        builder.setCancelable(false);
                        builder.show();
                        break;
                }
            }
        });

        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }
        });
        webview.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        WebView webview = findViewById(R.id.webview_of_simple_activity);
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
