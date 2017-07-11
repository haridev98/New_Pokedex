package com.hd.New_Pokedex;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.hd.New_Pokedex.R.id.iv;

public class MainActivity extends AppCompatActivity  {
    final static String TAG="queue";
    EditText text;
    String s;
    TextView t;
    ImageView im;
    // final static String TAG ="aaaaaa";
    StringBuffer buff=new StringBuffer();
    sqlhelper sql;
    Bitmap cam;

    boolean b = true;
    model mox = new model();

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text= (EditText) findViewById(R.id.editText);
        t= (TextView)findViewById(R.id.tb);
        im= (ImageView) findViewById(iv);
        // o={ds,text.getText().toString()};





    }

    public  void  lisen(View view) {
        s="http://pokeapi.co/api/v2/pokemon/"+text.getText().toString().toLowerCase();
        Toast.makeText(this,s,Toast.LENGTH_LONG);
        Log.d(TAG, "lisen: "+s);


        // t.setText(u);
        new  Jsoni().execute(s);
        /*Intent intent= new Intent(this, fr1.class);
        intent.putExtra("text",s);
        startActivity(intent);*/
    }
    public void click(View view){
         Intent intent = new Intent(this,list.class);
        startActivity(intent);
        /*SQLiteDatabase db = sql.getWritableDatabase();
        String [] a={sqlhelper.uid,sqlhelper.caption,sqlhelper.im};
        Cursor cursor= db.query(sqlhelper.tsblename,a,null,null,null,null,null);
        while (cursor.moveToNext()){
            int h= cursor.getInt(cursor.getColumnIndex(sqlhelper.uid));
            //   byte[] imd =  cursor.getBlob(cursor.getColumnIndex(sqlhelper.im));
            String s = cursor.getString(cursor.getColumnIndex(sqlhelper.caption));
            Log.d("fcuk", "onCreate: "+s);

        }*/}
    public  class Jsoni extends AsyncTask<String,String,Boolean>
    {

        @Override
        protected Boolean doInBackground(String... params) {
            HttpURLConnection connection=null;
            InputStream stream= null;
            String p="";
            BufferedReader reader =null;
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //b=true;




            try {
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                stream=connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(stream));
                String lines = "";
                while ((lines = reader.readLine()) != null) {
                    buffer.append(lines);
                }
                p=buffer.toString();

                try {
                    Log.d(TAG, "doInBackground: sssss");
                    JSONObject parent = new JSONObject(p);
                    Log.d("queu34", "doInBackground: "+parent.toString());
                    mox.detail="DUM";
                    //mox.detail=parent.getString("detail");
                    if(mox.detail=="null")
                        b=false;


                    else{
                        Log.d("queue", "doInBackground: "+parent.toString());
                        //mox.name=parent.getString("name");
                        mox.setName(parent.getString("name"));
                        Log.d("joa", "doInBackground: name:"+mox.getName());
                        // Log.d("zex", "doInBackground: "+mox.name);
                        mox.height=parent.getString("height");
                        Log.d("joa", "doInBackground: height:"+parent.getString("height"));
                        mox.weight=parent.getString("weight");
                        mox.base_experience=parent.getInt("base_experience");
                        mox.id=parent.getInt("id");
                        JSONArray arr = parent.getJSONArray("moves");
                        JSONObject ob,oc;

                        buff.append("\nMoves:");
                        //Log.d("bill", "doInBackground: "+oc.toString());
                        for (int i=0;i<arr.length();i++){
                            ob=arr.getJSONObject(i);
                            oc=ob.getJSONObject("move");
                            Log.d("jake", "doInBackground: "+oc.getString("name"));
                            buff.append("\nname"+Integer.toString(i+1)+": "+oc.getString("name"));

                        }
                        mox.moves=buffer.toString();
                        Log.d("bill", "doInBackground: "+buff.toString());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {


                connection.disconnect();
                //reader.close();

                try {
                    cam  = Glide.
                       with(MainActivity.this).
                       load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+mox.id+".png").
                       asBitmap().
                       into(100, 100). // Width and height
                       get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }



            return b;


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("queue3", "onPostExecute: ");
            if(b=false){
                t.setText("Not found");
                Log.d("qqqqq", "Not found");
            }
            else{
                im.setImageBitmap(cam);
                byte[] k=getBytes(cam);
                t.setText("Name: "+mox.name+"\nHeight: "+mox.height+"\nWeight:"+mox.weight+"\nBase Experience: "+mox.base_experience+"\nID: "+mox.id);
                Log.d("qqqqqq", "Name: "+mox.name+"\nHeight: "+mox.height);
                sql=new sqlhelper(MainActivity.this);
                SQLiteDatabase db= sql.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(sqlhelper.detail,mox.name);
                cv.put(sqlhelper.im,k);
                db.insert(sqlhelper.tsblename,null,cv);
                db.close();

            }
        }
    }
}
