package com.example.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.viewmodel.AcademicViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LupanullaMainScreen(
    viewModel: AcademicViewModel,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                
                // Load local assets single-file HTML web app matching exactly what is requested
                loadUrl("file:///android_asset/index.html")
            }
        },
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    )
}
