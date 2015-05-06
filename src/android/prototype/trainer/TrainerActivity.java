package android.prototype.trainer;

import android.app.Activity;
import android.os.Bundle;
import android.prototype.R;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class TrainerActivity extends Activity
{
	private TextView textNumNetworks;
	private TextView textNumNetworksAnswer;
	private TextView namesAndValues;
	private EditText binNumber;
	
	
	public void onCreate(Bundle SavedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.trainer);
		
		textNumNetworks = (TextView)findViewById (R.id.textNumNetworks);
		textNumNetworksAnswer = (TextView) findViewById (R.id.textNumNetworksAnswer);
		namesAndValues = (TextView) findViewById (R.id.namesAndValues);
		binNumber = (EditText) findViewById (R.id.binNumber);
		Bundle extras = getIntent().getExtras();

		if ( extras == null ){
		    Log.e("extras", "Extra NULL");
		} else {
			int numNetworks = extras.getInt("numNetworks");
			textNumNetworksAnswer.append("" + numNetworks);
			
			namesAndValues.append(extras.getStringArrayList("levels")+"\n\n" );
			
			
			for (int i= 0; i < extras.getStringArrayList("levels").size(); i++)
			{
				
				namesAndValues.append("\n" + extras.getStringArrayList("SSID").get(i)
								  + " : " + extras.getStringArrayList("levels").get(i));
			}
		}	
	}
}
