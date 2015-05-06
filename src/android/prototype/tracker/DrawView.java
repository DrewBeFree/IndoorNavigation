package android.prototype.tracker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.prototype.R;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class DrawView extends View {
	public static final String TAG = "Bitmap";
    Paint paint = new Paint();
	float x; 
	float y;
	Bitmap bitmap;
	Long time;
	Canvas canvas;
	private float scale =(float) .5;
    private float shiftX=0f;
    private float shiftY=0f;
    private float lastX=-1f;
    private float lastY=-1f;
//    private int mapWidth = 1400; 
//    private int mapHeight = 940;
     
    
    public DrawView(Context context,  float x, float y) 
    {
        super(context);  
        this.x = x; 
		this.y = y;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_0121);
		
    }
    
    final public void drawMap(Canvas c,float x1,float y1,float scale,Paint p){
       // c.drawLine(x1+shiftX, y1+shiftY, x2+shiftX, y2+shiftY, p);
    	c.scale(scale, scale);
        c.drawBitmap(bitmap, x1 +shiftX, y1 +shiftY, paint);
    }
    

    
    final public void drawPointer(Canvas c, float x1, float y1, Paint p){
    	//c.drawCircle(x1+shiftX, y1+shiftY, 20, p);
    	Resources res = getResources();
    	 Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
    	 c.drawBitmap(bitmap, x1+shiftX, y1+shiftY, p);
    }
    

    @Override
    public void onDraw(Canvas canvas) {
    	
    	canvas.save();
    	
    	Log.d("Height", ""+ bitmap.getHeight());
        drawMap(canvas, 0, 0, scale, paint);
        drawPointer(canvas, (bitmap.getWidth()*(float) .5) ,(bitmap.getHeight()*(float) .5), paint);
      //  Toast.makeText(getContext(), ""+ canvas.getMatrix(), Toast.LENGTH_LONG).show();
        
        canvas.restore();
  
    }
    
    
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input); // decodeStream seems a little slow
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
        int eventaction=event.getAction();
        
        float x=event.getX();
      
        
        
        float y=event.getY();
       
        
        
        switch(eventaction){
        case MotionEvent.ACTION_DOWN:
            time=System.currentTimeMillis();//used in the ACTION_UP case
            break;
            
        case MotionEvent.ACTION_MOVE:
        	
            if(lastX==-1)
            {
            	lastX=x;lastY=y;   //initializing X, Y movement
            } 
            else
            {
                if(Math.abs(x-lastX)>1 || Math.abs(y-lastY)>1)
                {	// Prevents Jittery Movement
                	
                	shiftX+=(x-lastX);  // Moves the Shifting Variables
                    shiftY+=(y-lastY); // In the Direction of Finger Movement
                    lastX=x; // Calculates Movement Delta
                    lastY=y;
                    invalidate();// Redraws View
                }
            }
            break;
            
            
        case MotionEvent.ACTION_UP: //this segment is to see whether a press is a selection click(quick press)
        //or a drag(long press)
            lastX=-1;
            if(System.currentTimeMillis() - time < 150)
                try 
            {
                    onClickEvent(x,y);//custom function to deal with selections
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            break;
        }
        return true;
    }

	private void onClickEvent(float x2, float y2) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), ""+x2 + " " + y2, Toast.LENGTH_LONG).show();
		
	}

	public void setScale(float i) 
	{
		scale = i;
	//
		invalidate();
		//onDraw(canvas);
	//	drawMap(canvas, lastX, lastY, scale, paint);
	}
	
	public float getScale()
	{
		return scale;
	}
}




