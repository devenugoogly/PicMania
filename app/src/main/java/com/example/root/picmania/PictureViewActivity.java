package com.example.root.picmania;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.supportedfiles.AlbumClass;
import com.example.supportedfiles.CustomDataClass;
import com.example.supportedfiles.GridAlbumViewAdapter;
import com.example.supportedfiles.GridPictureViewAdapter;
import com.example.supportedfiles.NetworkActivity;
import com.example.supportedfiles.PictureClass;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltFile;
import com.raweng.built.BuiltImageDownloadCallback;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltUser;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;
import com.raweng.built.view.BuiltImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


<<<<<<< HEAD
public class PictureViewActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener{
=======
public class PictureViewActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
>>>>>>> 7573d79f316fa540e25837075a3892a2c1b20a4e

    private String album_name;
    private List<PictureClass> dataItems;
    private GridPictureViewAdapter adapter;
    private GridView view;
<<<<<<< HEAD
    private int pageNumber;
    private BuiltUser builtUserObject;
=======
    private Button left,right;
    private TextView textView;
    private int page_number;
    private int totalObjects;
    private final static int LIMIT = 12;
    private int totalPage ;
>>>>>>> 7573d79f316fa540e25837075a3892a2c1b20a4e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);

        page_number = 1;
        view = (GridView)findViewById(R.id.gridView3);
<<<<<<< HEAD

        builtUserObject = new BuiltUser();
=======
        //view.setOnScrollListener(this);

        left = (Button)findViewById(R.id.button2);
        right = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);
>>>>>>> 7573d79f316fa540e25837075a3892a2c1b20a4e

        dataItems = new ArrayList<PictureClass>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            album_name = extras.getString("album_name");
            fetchPictures(0);
        }

        textView.setText("Page 0/0");
    }

    private void fetchPictures(int skipSize) {
        BuiltQuery query = new BuiltQuery("picture");

        query.where("album",album_name);
<<<<<<< HEAD
//        query.limit(4);
//        query.skip(skipSize);
=======
        query.limit(LIMIT);
        query.skip(skipSize);
>>>>>>> 7573d79f316fa540e25837075a3892a2c1b20a4e

        query.exec(new QueryResultsCallBack() {
            List<BuiltObject> pictures;
            @Override
            public void onSuccess(QueryResult queryResultObject) {
                // the queryResultObject will contain the objects of the class
                // here's the object we just created
                pictures = queryResultObject.getResultObjects();
                Log.i("Image Data",queryResultObject.getResultObjects().get(0).getJSONObject("image").toString());

                for(BuiltObject object : pictures){
                    Log.i("Data", "Name " + object.get("name"));
                    Log.i("Data","Title "+object.get("caption"));
                }
                totalObjects = pictures.size();
                totalPage = (int)Math.ceil(totalObjects/(double)LIMIT);

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
                if(pictures.size() > 0){
                    updatePictures(pictures);
                }
            }
        });
    }


    private void updatePictures(List<BuiltObject> pictures)
    {
        Log.i("Size"," "+pictures.size());
//        JSONObject jsonField = pictures.get(0).get("image").toJSON();
////        Log.i("JSON OBJECT",jsonField.toString());
//        try {
//            String imageUrl = jsonField.getString("url");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        for (BuiltObject obj : pictures) {
            JSONObject jsonField = obj.getJSONObject("image");
            String url = null;
            try {
                url = jsonField.get("url").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("URL",url);
            PictureClass objPic = new PictureClass(obj.get("name").toString(), obj.get("caption").toString(),url );
            dataItems.add(objPic);

        }
        Log.i("Size"," "+dataItems.size());

        adapter = new GridPictureViewAdapter(this, dataItems);
        GridView gridView = (GridView) findViewById(R.id.gridView3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        textView.setText("Page "+page_number+" of "+totalPage);


    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_view, menu);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.i("Position"," "+position);
//        AlbumClass objAlbum = dataItems.get(position);
////        if(objAlbum!=null){
////            NetworkActivity pictureNetworkActivity = new NetworkActivity(CustomDataClass.FETCH_PICTURE,AlbumActivity.this,builtUserObject);
////            pictureNetworkActivity.execute(objAlbum.title);
////        }
//        Intent pictureIntent = new Intent(this,PictureViewActivity.class);
//        pictureIntent.putExtra("album_name",objAlbum.title);
//        startActivity(pictureIntent);

        Log.i("Position"," "+position);
        PictureClass objPicture = dataItems.get(position);
//        if(objAlbum!=null){
//            NetworkActivity pictureNetworkActivity = new NetworkActivity(CustomDataClass.FETCH_PICTURE,AlbumActivity.this,builtUserObject);
//            pictureNetworkActivity.execute(objAlbum.title);
//        }
        Intent pictureIntent = new Intent(this,PictureViewActivity.class);
        pictureIntent.putExtra("album_name",objPicture.name);
        startActivity(pictureIntent);


    }


<<<<<<< HEAD
=======

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button3 && page_number<totalPage){
            dataItems.clear();
            page_number++;
            int skipSize = (page_number-1) * LIMIT;
            fetchPictures(skipSize);
        }
        else if(v.getId() == R.id.button2 && page_number>1){
            dataItems.clear();
            page_number--;
            int skipSize = (page_number-1)*LIMIT;
            fetchPictures(skipSize);
        }
    }
>>>>>>> 7573d79f316fa540e25837075a3892a2c1b20a4e
}
