package com.mndcuration.web.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

	WebView webview;
	private boolean mFlag = false;
	private final int QUIT_DELAYED_TIME = 2000;
	
	private Handler handler= new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == 0){
				mFlag = false;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_main);

		webview = (WebView) findViewById(R.id.webview2);
		
		webview.loadUrl("http://www.mndcuration.com");
		webview.setPadding(0, 0, 0, 0);
		int PIC_WIDTH= webview.getRight()-webview.getLeft();
		webview.setInitialScale(getScale(PIC_WIDTH));
		WebSettings settings = webview.getSettings();
		
    settings.setJavaScriptEnabled(true);
    settings.setLoadWithOverviewMode(true);
    
		webview.setWebViewClient(new WebClient());
	}
	private int getScale(int PIC_WIDTH){
		Point p = new Point();
	    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
	    display.getSize(p);
	    int width = p.x;
	    Double val = new Double(width)/new Double(PIC_WIDTH);
	    val = val * 100d;
	    return val.intValue();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		   if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {

		        webview.goBack();

		        return false;

		    }
		   else{
			   if(!mFlag){
				   Toast.makeText(this, "press back again to quit", Toast.LENGTH_SHORT).show();
				   	mFlag = true;
				   	handler.sendEmptyMessageDelayed(0, QUIT_DELAYED_TIME);
				   	return false;
			   } else{
				   finish();
			   }
		   }

		   return super.onKeyDown(keyCode, event);
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
