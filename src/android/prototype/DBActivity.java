package android.prototype;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DBActivity extends Activity {

private DatabaseHandler databaseHandler;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.db);
      ListView listContent = (ListView)findViewById(R.id.contentlist);
    
      /*
       *  Create/Open a SQLite database
       *  and fill with dummy content
       *  and close it
       */
      databaseHandler = new DatabaseHandler(this);
      databaseHandler.openToWrite();
      databaseHandler.deleteAll();

      databaseHandler.insert("A for Apply");
      databaseHandler.insert("B for Boy");
      databaseHandler.insert("C for Cat");
      databaseHandler.insert("D for Dog");
      databaseHandler.insert("E for Egg");
      databaseHandler.insert("F for Fish");
      databaseHandler.insert("G for Girl");
      databaseHandler.insert("H for Hand");
      databaseHandler.insert("I for Ice-scream");
      databaseHandler.insert("J for Jet");
      databaseHandler.insert("K for Kite");
      databaseHandler.insert("L for Lamp");
      databaseHandler.insert("M for Man");
      databaseHandler.insert("N for Nose");
      databaseHandler.insert("O for Orange");
      databaseHandler.insert("P for Pen");
      databaseHandler.insert("Q for Queen");
      databaseHandler.insert("R for Rain");
      databaseHandler.insert("S for Sugar");
      databaseHandler.insert("T for Tree");
      databaseHandler.insert("U for Umbrella");
      databaseHandler.insert("V for Van");
      databaseHandler.insert("W for Water");
      databaseHandler.insert("X for X'mas");
      databaseHandler.insert("Y for Yellow");
      databaseHandler.insert("Z for Zoo");
    
      databaseHandler.close();

      /*
       *  Open the same SQLite database
       *  and read all it's content.
       */
      databaseHandler = new DatabaseHandler(this);
      databaseHandler.openToRead();

      Cursor cursor = databaseHandler.queueAll();
      startManagingCursor(cursor);

      String[] from = new String[]{DatabaseHandler.KEY_CONTENT};
      int[] to = new int[]{R.id.text};

      SimpleCursorAdapter cursorAdapter =
       new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);

      listContent.setAdapter(cursorAdapter);
    
      databaseHandler.close();

    
  }
}