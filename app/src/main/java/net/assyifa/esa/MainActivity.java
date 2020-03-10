package net.assyifa.esa;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.assyifa.esa.R;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("http://202.62.9.132/dashboard_android_new");

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null) {
            webView.setWebViewClient(new WebViewClient(){
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                    //webView.loadUrl("file:///android_asset/error.html");
                    view.loadData("Maaf koneksi terputus", "text/html", "utf-8");
                    super.onReceivedError(view, request, error);
                }


                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse){
                    view.loadData("Maaf koneksi terputus", "text/html", "utf-8");
                    super.onReceivedHttpError(view, request, errorResponse);
                }


            });
            // finish();
        }
        else
        {
            webView.setWebViewClient(new WebViewClient());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda akan keluar ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();

    }
}
