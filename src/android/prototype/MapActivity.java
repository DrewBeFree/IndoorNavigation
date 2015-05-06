package android.prototype;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;



public class MapActivity extends Activity {

	
	@SuppressWarnings("unused")
	private MyCustomView tempView;
	private WebView myWebView = null;  
	public ZoomButtonsController zoomy;
	
	public static Context context; 
    int mWidth;
    int mHeight;
    Bitmap bitmap;
    ImageView imgView;
    ImageView imView;
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
         
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       
       
     //  doWork();
       
       
        
        
       	context = this;
       // this.setContentView(R.layout.webview);  
   //     this.myWebView = (WebView) this.findViewById(R.id.web2);  
        myWebView = new WebView(this);
        setContentView(myWebView);
        WebSettings webSettings;
        webSettings = myWebView.getSettings();
        webSettings.setPluginsEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(false);
//        FrameLayout mContentView = (FrameLayout) getWindow().  
//        getDecorView().findViewById(android.R.id.content);  
        
        this.myWebView.loadUrl("https://hdcontent.homedepot.com/mcontent/Mobile/Store_Maps/Converted_Maps/Map_0121.png");  
        Canvas canvas = new Canvas();
        Paint p = new Paint();
	    p.setColor (Color.RED);


    	Resources res = getResources();
   	 Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
   	 canvas.drawBitmap(bitmap,20, 20, p);
        
        this.myWebView.draw(canvas);
//        myWebView.setWebViewClient(new WebViewClient() {
//        	public void onPageFinished(WebView view, String url)
//            {
//                    Picture picture = view.capturePicture();
//                    
//                    Log.d("GHeight 2", "" +picture.getHeight());
//                    Bitmap  b = Bitmap.createBitmap( picture.getWidth(),
//            		picture.getHeight(), Bitmap.Config.ARGB_8888);
//                    
//
//                    Canvas c = new Canvas( b );
//
//                    picture.draw( c );
//            
//                    FileOutputStream fos = null;
//
//                    try 
//                    {
//
//                    	fos = new FileOutputStream( "/sdcard/yahoo_" +
//                    		System.currentTimeMillis() + ".png" );
//
//                    	if ( fos != null )
//                    	{
//                            b.compress(Bitmap.CompressFormat.PNG, 90, fos 
//                            		);
//
//                            fos.close();
//                    	}
//
//                    } 
//                    catch( Exception e )
//                    {
//                    //...
//                    }
//            }
//      });
        



        this.myWebView.getZoomControls();

     //   zoom.setVisibility(View.GONE);  
    //    ImageView test = new ImageView(context);
        myWebView.setInitialScale(100);
       
//
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//       lp.alignWithParent = true;
//        test.setLayoutParams(lp);
//
//        test.setPadding(200, 250, 0, 0);
//        
//        Log.d("Get Height", test.getContentDescription()+"");
//        
//        
        
        
//        myWebView.addView(test);
        
    //    myWebView.addView(new MyCustomView (getApplicationContext(), 300, 500));
       


       
    }
	

//    public void doWork()
//    {
//        tempView = new MyCustomView(getBaseContext(), 300, 700);
//    }
    
    


	//	Read more: http://getablogger.blogspot.com/2008/01/android-download-image-from-server-and.html#ixzz1xsXiOIpd
}






       