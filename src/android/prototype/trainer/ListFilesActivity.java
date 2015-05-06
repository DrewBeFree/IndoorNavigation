package android.prototype.trainer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
 
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
 
public class ListFilesActivity extends ListActivity {
  
 private List<String> fileList = new ArrayList<String>();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        File root = new File(Environment
          .getExternalStorageDirectory()
          .getAbsolutePath());
        ListDir(root);
 
    }
     
    void ListDir(File f){
     File[] files = f.listFiles();
     fileList.clear();
     for (File file : files){
      fileList.add(file.getPath());  
     }
      
     ArrayAdapter<String> directoryList
     = new ArrayAdapter<String>(this,
       android.R.layout.simple_list_item_1, fileList);
     setListAdapter(directoryList); 
    }
 
}
