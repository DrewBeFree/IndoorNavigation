package android.prototype.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class DesiredFunctionality 
{
	private ArrayList<PointWithRSSI> points;
	private PointWithRSSI currentPoint;
	
	DesiredFunctionality() 
	{
		points = new ArrayList<PointWithRSSI>();
		currentPoint = new PointWithRSSI(0,0, "Weight");
	}
	
	public void Print () 
	{
		for (PointWithRSSI p: points) 
		{
			System.out.print(p.getPoint().getX()+" "+p.getPoint().getY()+" ");
			for (String s: p.getWeightedaccessPoints().keySet()) {
				System.out.print(s+" "+p.getWeightedaccessPoints().get(s).getRssi() + " " );//+" "+p.getWeightedaccessPoints().get(s).getWeight());
			}
			
		}
	}
	
	public String Output () 
	{		
		StringBuffer sb = new StringBuffer();
		for (PointWithRSSI p: points) {
			sb.append(p.getPoint().getX()+" "+p.getPoint().getY()+" ");
			//System.out.print(sb);
			for (String s: p.getWeightedaccessPoints().keySet()) 
			{
				if (p.WeightedaccessPoints.get(s).getWeight() > 2) {
					sb.append(s+" "+p.getWeightedaccessPoints().get(s).getRssi()+" "); //+" "+p.getAccessPoints().get(s).getWeight() Figure we don't need to read weight. That way input object can be a map <String, String>
				}
			}
			sb.append("\n");
		}//
		sb.append("\n");
		return sb.toString();
	}
	
	public String lookupOutput () {		
		StringBuffer sb = new StringBuffer();
		PointWithRSSI p = currentPoint;
		sb.append(p.getPoint().getX()+" "+p.getPoint().getY()+" ");
		for (String s: p.getWeightedaccessPoints().keySet()) {
			sb.append(s+" "+p.getWeightedaccessPoints().get(s).getRssi()+" " + p.WeightedaccessPoints.get(s).getWeight() + " "); //+" "+p.getAccessPoints().get(s).getWeight() Figure we don't need to read weight. That way input object can be a map <String, String>
		}
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}
	
	public String newOutput() throws IOException 
	{		
		StringBuffer sb = new StringBuffer();
		for (PointWithRSSI p: points) {
			sb.append(p.getPoint().getX()+" "+p.getPoint().getY()+" ");
			for (String s: p.getAccessPoints().keySet()) {
				sb.append(s+" "+p.getAccessPoints().get(s)+" "); //+" "+p.getAccessPoints().get(s).getWeight() Figure we don't need to read weight. That way input object can be a map <String, String>
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	public static DesiredFunctionality Read(String wholeThing) 
	{
		DesiredFunctionality read = new DesiredFunctionality();
		while (wholeThing.contains("\n") && wholeThing.length() > 1) 
		{
			String line = wholeThing.substring(0, wholeThing.indexOf("\n"));
			wholeThing = wholeThing.substring(wholeThing.indexOf("\n")+1);
			String [] tokens = line.trim().split("\\s+");
			int x = Integer.parseInt(tokens[0]);
			int y = Integer.parseInt(tokens[1]);
			PointWithRSSI.Point2D point = new PointWithRSSI.Point2D(x,y);
			HashMap<String, Float> accessPoints = new HashMap<String, Float> ();
			
			
			for (int i = 3; i < tokens.length; i+=2) {
				String bssid = tokens[i-1];
				Log.d("Output", " AP bssid: " + tokens[i-1]);
				Float rssi = Float.parseFloat(tokens[i]);
				Log.d("Output", " AP rssi: " + tokens[i]);
				accessPoints.put(bssid, rssi);
			}
			read.getPoints().add(new PointWithRSSI(point,accessPoints));
		} 
		return read;
	}
	
	public ArrayList<PointWithRSSI> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<PointWithRSSI> points) {
		this.points = points;
	}

	public PointWithRSSI getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(PointWithRSSI currentPoint) {
		this.currentPoint = currentPoint;
	}
}

