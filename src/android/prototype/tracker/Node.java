package android.prototype.tracker;

public class Node 
{
	private String xCoordinate;
	private String yCoordinate; 
	private String deviceId;

	public Node(String x, String y, String deviceId)
	{
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.deviceId = deviceId;
	}
}