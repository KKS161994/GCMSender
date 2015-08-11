package android.turhanoz.com.gcmsender;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button sendMessage;
    GoogleCloudMessaging gcm;
    String sender_id = "1090005920230";
    String msgId = "dqG3vAqT1Wo:APA91bG8i_2JRq4sR2VGyLRJ4g1Joqw0nw7H8m2eNDzRkT4k97c_hugBqIxF8cb3O3YiOJ-J7vYV_ranZzAeUPUHsE-KRZtrOa0vg8omlCoq8I5kO50iAdTYrhYX9Gal4el0W7_MqLMi";
    URL url;
    ArrayList<String> arrayList;
    JSONObject jsonObject, subsetjson;
    String fetchedData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
        jsonObject = new JSONObject();
        subsetjson = new JSONObject();
        /*
        new JSONArray("[\"john\", \"mat\", \"jason\", \"matthew\"]")
        */
        arrayList = new ArrayList<>();
        arrayList.add("dqG3vAqT1Wo:APA91bG8i_2JRq4sR2VGyLRJ4g1Joqw0nw7H8m2eNDzRkT4k97c_hugBqIxF8cb3O3YiOJ-J7vYV_ranZzAeUPUHsE-KRZtrOa0vg8omlCoq8I5kO50iAdTYrhYX9Gal4el0W7_MqLMi");
        try {
            jsonObject.put("registration_ids", new JSONArray(arrayList));
            subsetjson.put("message", "Hello");
            jsonObject.put("data",subsetjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask() {

                    @Override
                    protected String doInBackground(Object... params) {
                        String msg = "Hello broo whats up";
             /*           try {
                            Bundle data = new Bundle();
                            data.putString("my_message", "Hello World");
                            data.putString("my_action",
                                    "com.google.android.gcm.demo.app.ECHO_NOW");
                            String id = msgId;
                            gcm.send(sender_id + "@gcm.googleapis.com", id, data);
                            msg = "Sent message";
                        } catch (IOException ex) {
                            msg = "Error :" + ex.getMessage();
                        }
             */
                        try {
                            HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://android.googleapis.com/gcm/send").openConnection();
                            urlConnection.setDoOutput(true);
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setRequestProperty("Authorization", "key=AIzaSyDlpz17AeMuhR2OkwZmDHjukqmrSnQ8yp8");
                            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                            wr.write(jsonObject.toString());
                            wr.flush();

                            urlConnection.connect();

                            InputStream is = urlConnection.getInputStream();
                            BufferedReader br = null;
                            StringBuilder sb = new StringBuilder();
                            String line;
                            try {
                                br = new BufferedReader(new InputStreamReader(is));
                                while ((line = br.readLine()) != null) {
                                    sb.append(line);
                                }
                                fetchedData = sb.toString();
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        return fetchedData;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        super.onPostExecute(o);
                        Toast.makeText(MainActivity.this, "Message Sent" + o, Toast.LENGTH_SHORT).show();
                    }
                }.execute(null, null, null);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
