package Challenge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class BookRunnable implements Runnable{
  private Thread t;
  private String threadName;
  private boolean flag = false;

  BookRunnable(String s) {
    this.threadName = s;
  }

  @Override
  public void run() {
    if (!flag) {
      JSONParser jsonParser = new JSONParser();
      //FileReader reader = new FileReader(filepath);
      InputStreamReader reader = null;
      try {
        reader = new InputStreamReader(new URL(this.threadName).openStream());
      } catch (IOException e) {
        e.printStackTrace();
      }


      //Read JSON file
      Object obj = null;
      try {
        obj = jsonParser.parse(reader);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }
      JSONArray rawlist = (JSONArray) obj;

      if (rawlist.size() == 0) {
        this.flag = true;
      }

      for (int i = 0; i < rawlist.size(); i++) {
        JSONObject temp = (JSONObject) rawlist.get(i);
        GenreClassifier.books.add(
                new Book((String) temp.get("title"), (String) temp.get("description")));
      }
    }
  }

  public void start() throws InterruptedException {
    if (t == null) {
      t = new Thread (this, threadName);
      t.start ();
      t.join();
    }
  }

  public boolean getFlag() {
    return this.flag;
  }
}
