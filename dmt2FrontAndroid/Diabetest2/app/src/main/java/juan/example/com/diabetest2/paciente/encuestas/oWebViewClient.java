package juan.example.com.diabetest2.paciente.encuestas;

import android.app.ProgressDialog;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class oWebViewClient extends WebViewClient {

    private ProgressDialog mProgressDialog;


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        view.loadUrl(url);
        return true;
    }
}
