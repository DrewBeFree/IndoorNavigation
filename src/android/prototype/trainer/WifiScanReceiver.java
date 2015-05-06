package android.prototype.trainer;

import java.util.List;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiScanReceiver extends BroadcastReceiver 
{

	AndroidWifiMonitor t;
	AndroidWifiMonitor scanTest;

	public WifiScanReceiver(AndroidWifiMonitor scanTest) {
		super();
		this.scanTest = scanTest;
	}

	@Override
	public void onReceive(Context c, Intent intent) 
	{
		List<ScanResult> results = scanTest.wifi.getScanResults();
		ScanResult bestSignal = null;
		for (ScanResult result : results) 
		{
			if (bestSignal == null || WifiManager.compareSignalLevel(bestSignal.level,	result.level) < 0)
				bestSignal = result;
		}
		String message = String.format(
				"%s networks found. %s is the strongest. \n Signal Strength: %s dBm. \n Channel: %s", results.size(),
				bestSignal.SSID, bestSignal.level, bestSignal.frequency);
		Toast.makeText(scanTest, message, Toast.LENGTH_LONG).show();
	}
}
