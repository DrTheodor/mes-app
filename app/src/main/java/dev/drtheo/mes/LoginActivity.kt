package dev.drtheo.mes

import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dev.drtheo.mes.ui.screens.DnevnikViewModel

@Composable
fun LoginActivity(dnevnikViewModel: DnevnikViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Red
    ) {
        MyWebViewScreen(dnevnikViewModel)
    }
}

@Composable
fun WebViewWithUrlListener(
    url: String,
    onToken: (String) -> Unit
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            CookieManager.getInstance().removeAllCookies { }
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    if (request != null && request.url.toString() == "https://school.mos.ru/v1/oauth/userinfo") {
                        val bearer = request.requestHeaders.getValue("Authorization")

                        onToken(bearer.substring(7))
                    }

                    return super.shouldInterceptRequest(view, request)
                }
            }

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            clearCache(true)
        }
    }

    AndroidView(
        factory = { webView },
        update = { it.loadUrl(url) }
    )
}

@Composable
fun MyWebViewScreen(dnevnikViewModel: DnevnikViewModel) {
    Column(
//        modifier = Modifier.background(color = Color.Green)
    ) {
        WebViewWithUrlListener(
            url = "https://school.mos.ru",
            onToken = { dnevnikViewModel.login(it) }
        )
    }
}