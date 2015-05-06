package android.prototype.trainer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.prototype.MyCustomView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.prototype.trainer.Tracker;

 

public class SearchActivity extends ListActivity 
{
	//Intent i = new Intent(SearchActivity.this, TrackerwithProduct.class);
	//Bundle bundle = new Bundle();
	MyCustomView view;
            @Override        
            public void onCreate(Bundle savedInstanceState) {

                  super.onCreate(savedInstanceState);

                  // Get the elements for the list

                  String[] elements = getElements();
                  
                  //Create an ArrayAdapter

                  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                              android.R.layout.simple_list_item_1, elements);

                  //Set the elements using List Adapter

                  this.setListAdapter(arrayAdapter);

            }

            @Override
            protected void onListItemClick(ListView l, View v, int position, long id) {

                  super.onListItemClick(l, v, position, id);                    
                  Object o = this.getListAdapter().getItem(position);
                  String element = o.toString();
                 
                  String productString = null;
//                  switch (4) 
//                  {
//                  case 1:
//                  {
                  if (position ==0)
                  {
                	  Tracker.setProductX(200);
                	  Tracker.setProductY(210);
                	  finish();
                  }
                  if (position == 1)
                  {
                	  Tracker.setProductX(200);
                	  Tracker.setProductY(350);
                	  finish();
                  }
                  if (position == 2)
                  {
                	  Tracker.setProductX(200);
                	  Tracker.setProductY(465);
                	  finish();
                  }
                  if (position == 3)
                  {
                	  Tracker.setProductX(250);
                	  Tracker.setProductY(465);
                	  finish();
                  }

//                  break;
//                  case 2: productString = "Screw Driver";
//                  	Tracker.setProductX(160);
//                  	Tracker.setProductY(160);
//                  break;
//                  case 3: productString = "Lawn Mower";
//                       Tracker.setProductX(200);
//                       Tracker.setProductY(200);
//                  break; 
//                  case 4: productString = "Siding";
//                  	Tracker.setProductX(300);
//                  	Tracker.setProductY(300);
//                  	break;
//                  }
                  
//                  if (element.equals("Hammer"));
//                  {
//                	  Tracker.setProductX(150);
//                	  Tracker.setProductY(150);
//                	  finish();
////                	  bundle.putFloat("x", 303);
////                	  bundle.putFloat("y", 200);
////                	  i.putExtras(bundle);
////                	  startActivity(i);
//                  }
//                  if (element.equals("Lawn Mower"));
//                  {
//                	  Tracker.setProductX(60);
//                	  Tracker.setProductY(60);
//                	  finish();
////                	  bundle.putFloat("x", 30);
////                	  bundle.putFloat("y", 200);
////                	  i.putExtras(bundle);
////                	  startActivity(i);
//                  }
//                  if (element.equals("Screw Driver"));
//                  {
//                	  Tracker.setProductX(20);
//                	  Tracker.setProductY(20);
//                	  finish();
////                	  bundle.putFloat("x", 10);
////                	  bundle.putFloat("y", 10);
////                	  i.putExtras(bundle);
////                	  startActivity(i);
//                  }
//                  if (element.equals("Siding"));
//                  {
//                	  Tracker.setProductX(300);
//                	  Tracker.setProductY(300);
//                	  finish();
////                	  Tracker.pX = 10;
////                	  Tracker.pY = 400;
//               // 	  finish();
////                	  bundle.putFloat("x", 400);
////                	  bundle.putFloat("y", 400);
////                	  i.putExtras(bundle);
////                	  startActivity(i);
//                  }
             //     Toast.makeText(this, "Time now : "+ new Date() + "\n I like " + element, Toast.LENGTH_LONG).show();

            }

 

           

            public String[] getElements() {

                  return new String[] { "Roofing Nailer", "Ceiling Fan", "Lawn Mower", "Paint" };

            }

 

      }