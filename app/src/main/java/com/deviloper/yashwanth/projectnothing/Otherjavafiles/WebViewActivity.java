package com.deviloper.yashwanth.projectnothing.Otherjavafiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deviloper.yashwanth.projectnothing.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webview = (WebView) findViewById(R.id.webview);
        WebSettings wbst = webview.getSettings();
        wbst.setJavaScriptEnabled(true);
        webview.loadUrl(link);
        webview.setWebViewClient(new MyWebClient());//inner class
        //improve performance
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setSavePassword(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setSaveFormData(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setEnableSmoothTransition(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webview.canGoBack())
                    webview.goBack();
                else
                    finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebClient extends WebViewClient {
        ProgressDialog progressDialog;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(WebViewActivity.this, "Loading...", "Fetching Page Content");
                progressDialog.setInverseBackgroundForced(true);
                progressDialog.setCancelable(false);
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } catch (Exception exe) {
            }
        }
    }
}
