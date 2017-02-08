package com.bjdv.lib.utils.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 初始化WebView 设置
 */
public class WebViewUtils
{
    public static void initSettings(WebView webView)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);//自适应
        webView.getSettings().setSupportZoom(false);
        //缩放控制
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        //不使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.requestFocus();
    }
}
