package com.mndcuration.web.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_main);

		webview = (WebView) findViewById(R.id.webview2);
		WebSettings set = webview.getSettings();
		set.setJavaScriptEnabled(true);
		set.setCacheMode(WebSettings.LOAD_NO_CACHE);
		set.setSupportZoom(false);
		webview.loadUrl("http://www.mndcuration.com");
		webview.setWebViewClient(new WebClient());
	}

	private class WebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.startsWith("sms:")) {
				Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				startActivity(i);
				return true;
			}

			if (url.startsWith("kakaolink:")) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
				return true;
			}

			if (url.startsWith("tel")) {
				Intent i = new Intent(Intent.ACTION_DIAL);
				i.setData(android.net.Uri.parse(url));
				startActivity(i);

			} else {
				view.loadUrl(url);

			}

			return true;

		}
	}
}
