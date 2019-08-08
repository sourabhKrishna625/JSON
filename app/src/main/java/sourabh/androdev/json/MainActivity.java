package sourabh.androdev.json;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    Toast.makeText(MainActivity.this, "Internet Present", Toast.LENGTH_SHORT).show();
                    new GetDataAsync().execute("http://api.theappsdr.com/json/");
                }
                else{
                    Toast.makeText(MainActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo==null || !networkInfo.isConnected()
                ||(networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)){
            return false;
        }
        return true;
    }
    private class GetDataAsync extends AsyncTask<String,Void, ArrayList<Person>> {
        @Override
        protected void onPostExecute(ArrayList<Person> personArrayList) {
            if(personArrayList.size()>0){
                Log.d("demo",personArrayList.toString());
            }else{
                Log.d("demo","empty result");

            }

        }

        @Override
        protected ArrayList<Person> doInBackground(String... strings) {
            HttpURLConnection connection=null;
            ArrayList<Person> result=new ArrayList<Person>();
            String json=null;
            try {
                URL url=new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    json= IOUtils.toString(connection.getInputStream(),"UTF8");
                    JSONObject root=new JSONObject(json);
                    JSONArray persons=root.getJSONArray("persons");
                    for (int i=0;i<persons.length();i++){
                            JSONObject personJson=persons.getJSONObject(i);
                            Person person=new Person();
                            person.name=personJson.getString("name");
                            person.id=personJson.getLong("id");
                            person.age=personJson.getInt("age");

                        JSONObject addressJson=personJson.getJSONObject("address");

                        Adress adress=new Adress();
                        adress.line1=addressJson.getString("line1");
                        adress.city=addressJson.getString("city");
                        adress.state=addressJson.getString("state");
                        adress.zip=addressJson.getString("zip");

                        person.adress=adress;

                        result.add(person);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection !=null){
                    connection.disconnect();
                }
            }

            return result;
        }
    }


}
