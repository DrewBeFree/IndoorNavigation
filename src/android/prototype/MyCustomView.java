package android.prototype;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

public class MyCustomView extends View{
	private Bitmap bitmap; 
    private Paint p = new Paint();
    
    Canvas canvas;
	float x; 
	float y; 
	float pX;
	float pY;
	
	
	public MyCustomView(Context context, float x, float y, float pX, float pY)
	{
		super(context);
		this.x = x; 
		this.y = y; 
		this.pX = pX;
		this.pY = pY;
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		
		
	//	ImageView image = (ImageView) findViewById(R.drawable.ic_launcher);
	}
	
	

	protected void onDraw(Canvas pCanvas)
	{
		pCanvas.save();
		pCanvas.drawBitmap(getResizedBitmap(bitmap, 30, 30),x,y,null);
	    p.setColor (Color.RED);
	    pCanvas.drawCircle(pX, pY, 5, p) ;
	    drawProduct(pCanvas, pX, pY, p);
	//    super.onDraw (pCanvas);
	    pCanvas.restore(); 

	}
	
	private void drawProduct(Canvas c, Float pX, Float pY, Paint p) {
		// TODO Auto-generated method stub
		c.drawCircle(pX, pY, 10, p);
	}



	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(90);


        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
	
}
	

