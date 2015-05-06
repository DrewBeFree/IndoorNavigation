package android.prototype.trainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.prototype.R;
import android.prototype.trainer.PointWithRSSI.Point2D;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidWifiMonitor extends Activity 
{
	private static final String TAG = "Wifi Info";


	private static final String Output = "Output";

	WifiManager wifi;
	int x;
	int y;
	private BroadcastReceiver receiver;
	WifiReceiver receiverWifi;
	private TextView bssid;
	private TextView speed;
	private TextView mac;
	private TextView ssid;
	private TextView rssi;
	static DesiredFunctionality df;
	FileWriter writer;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	
		
		//Setup UI
		
		bssid = (TextView) findViewById(R.id.Bssid);
		speed = (TextView) findViewById(R.id.Speed);
		mac = (TextView) findViewById(R.id.Mac);
		ssid = (TextView) findViewById(R.id.Ssid);
		rssi = (TextView) findViewById(R.id.Rssi);
		
		wifi = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		df = new DesiredFunctionality();
		
		this.registerReceiver(this.receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		if (!wifi.isWifiEnabled())
		{
			wifi.setWifiEnabled(true);
			System.out.print("wifi turned on");
		}
		
		
	//	Button button = (Button) findViewById(R.id.button6);
		
//		button.setOnClickListener(new OnClickListener() 
//		{
//			
//			@Override
//			public void onClick(View v) 
//			{
// 
//			  Intent fileList =  new Intent(AndroidWifiMonitor.this, ListFilesActivity.class);
//			    startActivity(fileList);
// 
//			}
// 
//		});
	}
	
	
	private BroadcastReceiver myWifiReceiver
	= new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			//  Auto-generated method stub
			NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
				displayWifiState();
			}
		}};

	@SuppressWarnings("unused")
	public boolean displayWifiState() 
	{
	
		ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//		txtStatus.setText("");
		
	//	ssid.setText(wifi.getScanResults()+"");

		// Setup WiFi
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// Get WiFi status
		WifiInfo info = wifi.getConnectionInfo();
		receiverWifi = new WifiReceiver();
		registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifi.startScan();
		
//		txtStatus.append("\n\nWiFi Status: " + info.toString());



		Map<String, ScanResult> map = new HashMap <String, ScanResult>();
		
		boolean check = false;
		if (myNetworkInfo.isConnected())
		{ 
			// List available networks
			List<ScanResult> accessPoints = wifi.getScanResults();
			
		//	Toast.makeText(getApplicationContext(), "Setting AP", Toast.LENGTH_SHORT).show();
			//List for displaying info
			for (ScanResult result : accessPoints) 
			{
				String test = result.BSSID;
				
				if (df.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) 
				{
					AccessPoint ap = df.getCurrentPoint().getWeightedaccessPoints().get(test); 
					float a = ap.getRssi();
					float c = (float)result.level;
					if (a != c )
					{
						check = true;
					}
				}
				else
				{
					check = true;
				}
				ssid.setText(result.SSID+"\n");
				bssid.setText(result.BSSID+"\n");
				speed.setText(""+result.frequency+"\n");
				rssi.setText(""+result.level+"\n");
				
			}
			if (check == true) 
			{
				int i = 0;

				for (ScanResult sr: accessPoints) 
				{
					String test = sr.BSSID;
					if (df.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) 
					{ 
						
						//Current Point's HashMap contains current accesspoint's bssid
						AccessPoint ap = df.getCurrentPoint().getWeightedaccessPoints().get(test); 
						Log.d(Output, test + "Add to running average.  RSSI: "+ ap.getRssi() + " Weight: " + ap.getWeight()+1);
						  
						ap.setRssi((ap.getRssi()*(float)ap.getWeight()+(float)sr.level)/((float)(ap.getWeight()+1.0)));		//add to running average						
						ap.setWeight (ap.getWeight() + 1);
					}
					else 
					{
						df.getCurrentPoint().getWeightedaccessPoints().put(sr.BSSID, new AccessPoint((float)sr.level, sr.BSSID, 1));
						Log.d(Output, "Create new Access Point: " +sr.level + " " + sr.BSSID);
						i++;
					}
				}
				if (i != 0)
					Toast.makeText(this, "Unique Channels: " + i, Toast.LENGTH_SHORT).show();
				
			}
			//Toast.makeText(getApplicationContext(), "AP Set", Toast.LENGTH_SHORT).show();
			return check;
		}
		else
		{
			return false;
		}
	}
	public void refresh(View view) 
	{
		displayWifiState();
	}
	
				
	public void average(View view) 
	{
		EditText s =  (EditText)findViewById(R.id.xPosition);
		x = Integer.parseInt(s.getText().toString());
		s =  (EditText)findViewById(R.id.yPosition);
		y = Integer.parseInt(s.getText().toString());
		df.setCurrentPoint(new PointWithRSSI(x,y, "Weight"));
		
		//check to see if point was already in list
		for (PointWithRSSI p: df.getPoints()) 
		{
			if (p.getPoint().equals(df.getCurrentPoint().getPoint())) 
			{
				df.setCurrentPoint(p);
			}
			
		}			
		if (!df.getPoints().contains(df.getCurrentPoint())) 
		{
			//If point wasn't in list, add point to list
			df.getPoints().add(df.getCurrentPoint());
		//	Log.d(Output, "Averaging... add " + df.getCurrentPoint() + " to the list.");
		}
		
		int i=0;
		while (i<25) {
			if (displayWifiState()) 
			{
				i++; 
			}
		}
		Context context = getApplicationContext();
		CharSequence text = "Finished averaging";
		Toast toast = Toast.makeText(context, text, 1);
		toast.show();
	}
	 
	public void saveState(View view) throws IOException {
		
		int i=0;
		if (i==0);
		try {
			
			df.Print();
			serializeMap();
			
		}
		catch (Exception e) {
			System.out.println("Error doing File I/O");
		}
	}
	
	public void serializeMap() 
	{//HashMap<String,String> hm) {
		try 
		{ // catches IOException below
		//	final String TESTSTRING = new String("Hello Android");
			String output = df.Output();
			Log.d(Output, "Serialize output: " + output);
			Log.d(Output, ""+getFilesDir());

			
			// ##### Write a file to the disk #####
  
			Log.v("Output", Environment.getExternalStorageState());
			Toast.makeText(getApplicationContext(), Environment.getExternalStorageState(), Toast.LENGTH_SHORT);
			generateNoteOnSD("TestFile.txt", output);
//			FileOutputStream fOut = openFileOutput("samplefile.txt",
//					MODE_WORLD_READABLE);
//			OutputStreamWriter osw = new OutputStreamWriter(fOut); 
//			OutputStreamWriter out;
			
 
//			try 
//			{
//			    File path=new File(getFilesDir(),"myfolder");
//			    File mypath=new File(path,"myfile.txt");
//			    if (!mypath.exists()) {
//			        out = new OutputStreamWriter(openFileOutput( mypath.getAbsolutePath() , MODE_PRIVATE));
//			        out.write("test");
//			        out.close();
//			    }                           
//			}
//			catch (Exception e)
//			{
//				Log.v("IO Exception", e.getMessage());
//			}
			// Write the string to the file
//			osw.write(output);
//			/* ensure that everything is
//			 * really written out and close */
//			
//			osw.flush();
//			osw.close();
//			System.out.print("osw.close() " +fileList() );
		}
		catch (Exception e) 
		{
			Toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT);
			Log.v("IO Exception", e.getMessage());
		}
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStop() 
	{
	//	unregisterReceiver(receiver);
		super.onStop();

	}

	public void updateUI() 
	{
		startActivity(getIntent());
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	public void readData(View view) 
	{
		try 
		{
			Log.d(Output, Environment.getExternalStorageDirectory() +"/Cumberland Test/Test.txt");
			FileInputStream fIn = openFileInput(Environment.getExternalStorageDirectory() +"/Cumberland Test/Test.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			/* Prepare a char-Array that will
			 * hold the chars we read back in. */
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
			String readString = new String(inputBuffer, 0, index);
			//				if (readString.equals(df.Output())) {
			//					//
			//					System.out.println("It works!!");
			//				}
			

			Log.d(Output, "readData String: " + readString);
			df = DesiredFunctionality.Read(readString);		//lesser (absolute) decibel value means im closer to the access point..
			String s = df.newOutput();
			if (readString.equals(df.newOutput())) 
			{
				System.out.println("WifiMon ln 344 works");
			}

		}
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
		try 
		{
			FileInputStream fIn = openFileInput(Environment.getExternalStorageDirectory() +"Cumberland Test/Test.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			/* Prepare a char-Array that will
			 * hold the chars we read back in. */
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
					Log.d(Output, "Input Buffer length: " + inputBuffer.length);
					break;
				}
			}
			
			String readString = new String(inputBuffer, 0, index);
			if (readString.equals(df.Output())) 
			{
				//
				System.out.println("It works!!" + readString + " ahahha");
			}

			df = DesiredFunctionality.Read(readString);
			String s = df.newOutput();
			if (readString.equals(df.newOutput())) {
				System.out.println("It works !! (389)");
			}
 
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(AndroidWifiMonitor.this, TrainerActivity.class);
		
//		i.putExtras(bundle);
		switch (item.getItemId()) {
		case R.id.menuItemMap: startActivity(new Intent(AndroidWifiMonitor.this, Tracker.class));  
		break;
//		case R.id.menuItemTrain:startActivity(i);    
//		
//		break;

		}
		return true;
	}
	
	public boolean lookupAverage(DesiredFunctionality curr) 
	{
		ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
		//myWifiManager.

		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();
		registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifi.startScan();

		boolean check = false;
		if (myNetworkInfo.isConnected())
		{
			//myWifiManager.startScan();			//breaks code for some reason
			//while (!myWifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals("android.net.wifi.SCAN_RESULTS"));


			List<ScanResult> accessPoints = myWifiManager.getScanResults();	//use this to get all access points' information.
			//StringBuffer str = new StringBuffer();
			for (ScanResult sr: accessPoints) {
				String test = sr.BSSID;
				if (curr.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) {
					AccessPoint ap = curr.getCurrentPoint().getWeightedaccessPoints().get(test); 
					//float check = ap.getRssi()*(float)ap.getWeight()+(float)sr.level;
					float a = ap.getRssi();
					float c = (float)sr.level;
					if (a != c ){
						check = true;
					}
				}
				else {
					check = true;
				}
			}
			if (check == true) {
				for (ScanResult sr: accessPoints) {
					//str.append (sr.BSSID + '\t' + sr.level + '\n');
					String test = sr.BSSID;
					//curr.getCurrentPoint().getWeightedaccessPoints().clear();		//empty hash map -- probably not needed
					if (curr.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) { //Current Point's HashMap contains current accesspoint's bssid
						AccessPoint ap = curr.getCurrentPoint().getWeightedaccessPoints().get(test); 
						//float check = ap.getRssi()*(float)ap.getWeight()+(float)sr.level;
						float a = ap.getRssi();
						float b = sr.level;
						ap.setRssi((ap.getRssi()*(float)ap.getWeight()+(float)sr.level)/((float)(ap.getWeight()+1.0)));		//add to running average
						ap.setWeight (ap.getWeight()+1);
						System.out.print(ap);
					}
					else {
						curr.getCurrentPoint().getWeightedaccessPoints().put(sr.BSSID, new AccessPoint((float)sr.level, sr.BSSID, 1));
					}
				}
			}
		}
		return check;
	}

	public void lookup (View view) 
	{
		//first find a five times average to just get the values..
		DesiredFunctionality temp = new DesiredFunctionality();		//the coordinate part of this object is meaningless
		int i=0;
		while (i<10) 
		{
			if (lookupAverage(temp)) {
				i++;
			}
		}
		Context context = getApplicationContext();
		CharSequence text = "Finished lookup averaging";
		Toast toast = Toast.makeText(context, text, 1);
		toast.show();
		//now read df object
		//readData(view);
		//do containment first, then do closeness
		boolean containment = true;
		ArrayList<PointWithRSSI> matches = new ArrayList<PointWithRSSI> ();
		ArrayList<PointWithRSSI> compare= df.getPoints();
		Log.d("Size of df.getPoints() Line 492:  " ,"" + df.getPoints().size());
		Set<String> keys= temp.getCurrentPoint().WeightedaccessPoints.keySet();
		String as1 = temp.lookupOutput();
		//Set<String> keys = (Set<String>) temp.getPoints().get(0).getWeightedaccessPoints().keySet();		//this is from the real-time data
		//check containment using keys
		
		for (PointWithRSSI p : compare) {
			
			Log.d(Output, "APs: "+ p.getAccessPoints() + "Weighted APs size" + p.getWeightedaccessPoints().size() + " 2d point: " + p.getPoint());
			Log.d(Output, "(Line 500) getPoints() ="+p.getAccessPoints());
			containment = true;
			Set<String> c = p.getAccessPoints().keySet();
			//if (c.size() == keys.size()) {
			for (String s: keys) {
				if (!c.contains(s)) {
					containment = false;		//s is all the bssid's, which is unique for all the APs
				}
			}
			if (containment == true ) {		//containment probably not being used
				matches.add(p);
			}

			//}
		}	//end for
		int x;
		int y;

		PointWithRSSI minPoint = null;
		//dot product choose the minimum value
		if (matches.size() == 1) {		//never going to happen, it seems (in ENS at least)
			Point2D currPosition = matches.get(0).point;
			x = currPosition.getX();
			y = currPosition.getY();
		}
		else 
		{
			float minDot = Float.MAX_VALUE;
			//apply logic from above else loop here also
			for (PointWithRSSI p : compare) {
				float currDot = 0;
				Set<String> c = p.getAccessPoints().keySet();
				//if (c.size() == keys.size()) {
				for (String s: keys) {
					if (c.contains(s)) {
						currDot += p.getAccessPoints().get(s)*p.getAccessPoints().get(s) ;
						currDot -= (Math.abs(temp.getCurrentPoint().getWeightedaccessPoints().get(s).getRssi()) * Math.abs(p.getAccessPoints().get(s)));		//add to the dot product
					}
				}
				currDot = Math.abs(currDot);
				if (currDot < minDot) {
					minDot = currDot;
					minPoint = p;
				}
			}	//end for
		}	//end else

	} 
	public void generateNoteOnSD(String sFileName, String sBody){
	    try
	    {
	        File root = new File(Environment.getExternalStorageDirectory(), "Cumberland Test");
	        if (!root.exists()) {
	            root.mkdirs();
	        }
	        
	        File gpxfile = new File(root, sFileName);
	        
	        writer = new FileWriter(gpxfile);
	        writer.append(sBody);
	        writer.flush();
	        writer.close();
	        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();
	         Log.v("IO Exception", e.getMessage());
	    }
	   
	}   
	

}
class WifiReceiver extends BroadcastReceiver 
{
	public void onReceive(Context c, Intent intent) 
	{
		
	}
}
