package juan.example.com.diabetest2.paciente.encuestas;

import android.app.ProgressDialog;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class oWebViewClient extends WebViewClient {

    private ProgressDialog mProgressDialog;


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // This line right here is what you're missing.
        // Use the url provided in the method.  It will match the member URL!


        view.loadUrl(url);
        return true;
    }
}
