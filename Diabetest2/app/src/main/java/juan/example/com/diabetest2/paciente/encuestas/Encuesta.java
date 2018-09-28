package juan.example.com.diabetest2.paciente.encuestas;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import juan.example.com.diabetest2.R;

public class Encuesta extends AppCompatActivity {

    WebView web;
    WebView wbb;
    ProgressDialog mProgressDialog;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        /*
        web = new WebViewHelper().webview(Encuesta.this);
        web.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSccdhIrGrUD8c4yM-esrLXR22_t9ftPHbAps7xyepbN2HsSIw/viewform?usp=sf_link");

        // you can add JavaScript interface like this:
        // web.addJavascriptInterface(new YourJsInterfaceClass(), “Android”);

        // set web as content view
        setContentView(web);
        */
        mProgressDialog = new ProgressDialog(this);

        wbb = (WebView) findViewById(R.id.hola);
        WebSettings wbset=wbb.getSettings();
        wbset.setJavaScriptEnabled(true);
        wbb.setWebViewClient(new oWebViewClient());
        String url="https://docs.google.com/forms/d/e/1FAIpQLSccdhIrGrUD8c4yM-esrLXR22_t9ftPHbAps7xyepbN2HsSIw/viewform?usp=sf_link";
        mProgressDialog.setMessage("Cargando… " );
        wbb.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //show the user progress percentage
                mProgressDialog.setMessage("Cargando… " + progress + "%");
            }
            });
        wbb.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                //if there’s an error loading the page, make a toast
                Toast.makeText(este, description + ".", Toast.LENGTH_SHORT)
                        .show();

            }

            public void onPageFinished(WebView view, String url) {
                //after loading page, remove loading page
                mProgressDialog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);

                //on page started, show loading page
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Cargando…");
                mProgressDialog.show();

            }

        });
        wbb.loadUrl(url);
    }
}
