package android.prototype.tracker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.prototype.trainer.DesiredFunctionality;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.prototype.trainer.Tracker;

public class DisplayMapActivity extends Activity 
{
	
	static DesiredFunctionality df;		//this will be used to do calibration

	DrawView drawView;
	private String url = "https://hdcontent.homedepot.com/mcontent/Mobile/Store_Maps/Converted_Maps/Map_0121.png"; 
	Bitmap bitmap = getBitmapFromURL(url);
	
	
	@Override 
    public void onCreate(Bundle savedInstanceState) 
    {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
//        Bitmap bitmap = getBitmapFromURL(url);
 //       drawView = new DrawView(this, bitmap , 0,0);
        drawView.setBackgroundColor(Color.WHITE);
        setContentView(drawView);
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
	
	//two preset scale levels modifying the search button's functionality
//	 @Override
//	 public boolean onSearchRequested() {
//		 Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
//		 if (drawView.getScale() == (float).5)
//		 {
//			 drawView.setScale(1);
//		 }
//		 else
//			 drawView.setScale((float).5);
//	     return false;  // don't go ahead and show the search box
//	 }
//	 
	 public void readData(String readString) throws IOException {
			
			/*
			FileInputStream fIn = openFileInput("samplefile.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			char[] inputBuffer = new char[100000];
			CharBuffer c = null;
			// Fill the Buffer with data from the file
			isr.read(inputBuffer);
			// Transform the chars to a String
			int index = 0;
			for (int i =0; i< inputBuffer.length; i++) {
				char test = inputBuffer[i];
				if (test == '\u0000') {
					index = i;
					break;
				}
			}
			
			*/
			//String readString = new String(inputBuffer, 0, index);
			
							if (readString.equals(df.Output())) {
								//
								System.out.println("It works!!");
							}

			df = DesiredFunctionality.Read(readString);		//lesser (absolute) decibel value means im closer to the access point..
			String s = df.newOutput();
			if (readString.equals(df.newOutput())) 
			{
				System.out.println("It works !!");
			}
			
	 }
	 
	 }
	

