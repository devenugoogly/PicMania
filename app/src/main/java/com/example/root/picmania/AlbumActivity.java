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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.supportedfiles.AlbumClass;
import com.example.supportedfiles.CustomDataClass;
import com.example.supportedfiles.GridAlbumViewAdapter;
import com.example.supportedfiles.NetworkActivity;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltUser;

import java.util.ArrayList;
import java.util.List;



public class AlbumActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    BuiltUser builtUserObject = new BuiltUser();
    private List<AlbumClass> dataItems;
    private GridAlbumViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        dataItems = new ArrayList<AlbumClass>();
        fetchData();
    }


    private void fetchData(){
        NetworkActivity albumActivity = new NetworkActivity(CustomDataClass.FETCH_ALBUM, AlbumActivity.this,builtUserObject);
        albumActivity.execute();
//        BuiltQuery query = new BuiltQuery("album");
//
//        query.exec(new QueryResultsCallBack() {
//            List<BuiltObject> albums;
//            @Override
//            public void onSuccess(QueryResult queryResultObject) {
//                // the queryResultObject will contain the objects of the class
//                // here's the object we just created
//                albums = queryResultObject.getResultObjects();
//                for(BuiltObject object : albums){
//                    Log.i("Data","Name "+object.get("name"));
//                    Log.i("Data","Title "+object.get("title"));
//                }
//            }
//
//            @Override
//            public void onError(BuiltError builtErrorObject) {
//                // query failed
//                // the message, code and details of the error
//                Log.i("error: ", "" + builtErrorObject.getErrorMessage());
//                Log.i("error: ", "" + builtErrorObject.getErrorCode());
//                Log.i("error: ", "" + builtErrorObject.getErrors());
//            }
//
//            @Override
//            public void onAlways() {
//                // write code here that you want to execute
//                // regardless of success or failure of the operation
//
//            }
//        });
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
            AlbumClass objAlbum = new AlbumClass(obj.get("name").toString(),obj.get("title").toString(),getDrawable(R.drawable.album));
            dataItems.add(objAlbum);
        }

        adapter = new GridAlbumViewAdapter(this,dataItems);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

////        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
////        gridLayout.setColumnCount(3);
////        gridLayout.setRowCount(3);
//
//        GridView gridView = (GridView)findViewById(R.id.gridView);
//
//
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////
////        ArrayList<AlbumFragment> albumsList = new ArrayList<AlbumFragment>();
////
//        dataItems = new ArrayList<AlbumDemo>();
//        Log.i("Fragment","Inside update data");
//       for(BuiltObject obj : albums) {
////           albumsList.add(obj);
//////            Button btn = new Button(this);
//////            btn.setBackgroundResource(R.drawable.album);
//////            gridLayout.addView(btn);
////           Log.i("Fragment","Adding fragment "+gridLayout.getId());
//            AlbumDemo demo = new AlbumDemo();
////           albumsList.add(fragment);
//           Log.i("Data"," "+obj.get("name").toString());
////           dataItems.add(obj.get("name").toString());
//           dataItems.add(demo);
////
////
////            fragmentTransaction.add(gridLayout.getId(), fragment);
//        }
//        adapter = new ArrayAdapter<AlbumDemo>(this,android.R.layout.simple_list_item_1,dataItems);
////        ArrayAdapter<AlbumFragment> adapter = new ArrayAdapter<AlbumFragment>(this,android.R.layout.simple_gallery_item,albumsList);
//        gridView.setAdapter(adapter);
////        fragmentTransaction.commit();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Position"," "+position);
    }

    @Override
    public void onClick(View v) {

    }
}
