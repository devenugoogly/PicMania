package com.example.root.picmania;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.supportedfiles.AlbumClass;
import com.example.supportedfiles.CustomDataClass;
import com.example.supportedfiles.GridAlbumViewAdapter;
import com.example.supportedfiles.GridPictureViewAdapter;
import com.example.supportedfiles.NetworkActivity;
import com.example.supportedfiles.PictureClass;
import com.raweng.built.Built;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltUser;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{

    BuiltUser builtUserObject = new BuiltUser();
    private List<PictureClass> dataItems;
    private GridPictureViewAdapter adapter;
    private EditText searchText;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dataItems = new ArrayList<PictureClass>();

        try {
            Built.initializeWithApiKey(SearchActivity.this, "blt643f5d49ff2042cb", "0000011");
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchText = (EditText)findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void updateData(List<BuiltObject> pictures) {

        Log.i("Size"," "+pictures.size());
        for (BuiltObject obj : pictures) {
//            PictureClass objPic = new PictureClass(obj.get("name").toString(), obj.get("caption").toString(), getDrawable(R.drawable.album));
//            dataItems.add(objPic);
        }
        Log.i("Size"," "+dataItems.size());

        adapter = new GridPictureViewAdapter(this, dataItems);
        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Position", " " + position);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Log.i("Data","Button clicked");
            String searchString = searchText.getText().toString();
            Log.i("Text"," "+searchString);

            NetworkActivity searchNetworkActivity = new NetworkActivity(CustomDataClass.SEARCH_PICTURE,SearchActivity.this,builtUserObject);
            searchNetworkActivity.execute(searchString);

        }
    }
}
