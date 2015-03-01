package us.scoreme.locationpicker;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by markrose on 2/17/15.
 */
public class whereamI extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data);
        String userid=sph.getSharedPreferenceString(this,"userid","0");
        Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();

        webView = (WebView) findViewById(R.id.mywebview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new myWebViewClient());
        webView.loadUrl("http://www.scoreme.us/wifimagicmobile.php?userid="+userid);

    }

    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
