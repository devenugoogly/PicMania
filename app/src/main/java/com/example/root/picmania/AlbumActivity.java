package com.example.root.picmania;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.supportedfiles.AlbumClass;
import com.example.supportedfiles.CustomDataClass;
import com.example.supportedfiles.GridAlbumViewAdapter;
import com.example.supportedfiles.NetworkActivity;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltUser;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;

import java.util.ArrayList;
import java.util.List;



public class AlbumActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener{

    BuiltUser builtUserObject = new BuiltUser();
    private List<AlbumClass> dataItems;
    private GridAlbumViewAdapter adapter;
    private int page_number;
    private Button left,right;
    private TextView textView;
    private int totalObjects;
    private final static int LIMIT = 12;
    private int totalPage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        page_number = 1;

        left = (Button)findViewById(R.id.button2);
        right = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);

        left.setOnClickListener(this);
        right.setOnClickListener(this);

        dataItems = new ArrayList<AlbumClass>();
        textView.setText("Page 0/0");


        fetchData(0);
    }


    private void fetchData(int skipSize){
        BuiltQuery query = new BuiltQuery("album");
        query.skip(skipSize);
        query.limit(LIMIT);
        query.includeCount();
        Log.i("Method","Fetch Data Called");
        query.exec(new QueryResultsCallBack() {
            List<BuiltObject> albums;
            @Override
            public void onSuccess(QueryResult queryResultObject) {
                // the queryResultObject will contain the objects of the class
                // here's the object we just created
                albums = queryResultObject.getResultObjects();
                Log.i("Count", " " + queryResultObject.getCount());
                totalObjects = queryResultObject.getCount();
                totalPage = (int)Math.ceil(totalObjects/(double)LIMIT);
                for(BuiltObject object : albums){
                    Log.i("Data","Name "+object.get("name"));
                    Log.i("Data","Title "+object.get("description"));

                }
            }

            @Override
            public void onError(BuiltError builtErrorObject) {
                // query failed
                // the message, code and details of the error
                Log.i("error: ", "" + builtErrorObject.getErrorMessage());
                Log.i("error: ", "" + builtErrorObject.getErrorCode());
                Log.i("error: ", "" + builtErrorObject.getErrors());
            }

            @Override
            public void onAlways() {
                // write code here that you want to execute
                // regardless of success or failure of the operation
                if(albums.size() > 0){
                    updateData(albums);
                }
            }
        });
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album, menu);
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
        }else if(id == R.id.action_add){
            Intent addAlbumIntent = new Intent(this,AddAlbumActivity.class);
            startActivity(addAlbumIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void updateData(List<BuiltObject> albums){


        for(BuiltObject obj : albums){
            AlbumClass objAlbum = new AlbumClass(obj.get("name").toString(),obj.get("description").toString(),getDrawable(R.drawable.album));
            dataItems.add(objAlbum);
        }

        adapter = new GridAlbumViewAdapter(this,dataItems);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        textView.setText("Page "+page_number+" of "+totalPage);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Position"," "+position);
        AlbumClass objAlbum = dataItems.get(position);
//        if(objAlbum!=null){
//            NetworkActivity pictureNetworkActivity = new NetworkActivity(CustomDataClass.FETCH_PICTURE,AlbumActivity.this,builtUserObject);
//            pictureNetworkActivity.execute(objAlbum.title);
//        }
        Intent pictureIntent = new Intent(this,PictureViewActivity.class);
        pictureIntent.putExtra("album_name",objAlbum.title);
        startActivity(pictureIntent);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button3 && page_number<totalPage){
                dataItems.clear();
                page_number++;
                int skipSize = (page_number-1) * LIMIT;
                fetchData(skipSize);
        }
        else if(v.getId() == R.id.button2 && page_number>1){
            dataItems.clear();
            page_number--;
            int skipSize = (page_number-1)*LIMIT;
            fetchData(skipSize);
        }
    }


}
