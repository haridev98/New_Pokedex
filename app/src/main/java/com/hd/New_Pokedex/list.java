package com.hd.New_Pokedex;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class list extends AppCompatActivity {
    GridView gridView;
    modbaseadapter mod = null;
    ArrayList<single> list;
    sqlhelper sql;
    Context context;
    View l;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        gridView= (GridView) findViewById(R.id.gridview);
        sql= new sqlhelper(this);
        l=findViewById(R.id.list1);
        l.setBackgroundColor(Color.GRAY);
        context = this;
        list = new ArrayList<>();
        mod = new modbaseadapter(R.layout.single_view_layout,list,this);
        gridView.setAdapter(mod);
        db = sql.getWritableDatabase();
        String [] a={sqlhelper.uid,sqlhelper.detail,sqlhelper.im};
        Cursor cursor= db.query(sqlhelper.tsblename,a,null,null,null,null,null);
        while (cursor.moveToNext()){
           int h= cursor.getInt(cursor.getColumnIndex(sqlhelper.uid));
            byte[] imd =  cursor.getBlob(cursor.getColumnIndex(sqlhelper.im));
            String s = cursor.getString(cursor.getColumnIndex(sqlhelper.detail));
            list.add(new single(s,imd,h));}
        mod.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,pic.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });



    }
    public void gull(View v){
        db.delete(sqlhelper.tsblename,null,null);
        Intent i = new Intent(this,list.class);
        startActivity(i);
    }
    public  void bull(View v){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }


}
