package com.example.supportedfiles;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.root.picmania.AlbumActivity;
import com.example.root.picmania.LoginActivity;
import com.example.supportedfiles.CustomDataClass;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;

import java.util.List;

/**
 * Created by root on 4/25/15.
 */
public class NetworkActivity extends AsyncTask {

    private int workType;
    private Activity activity;
    private BuiltUser builtUserObject;

    public NetworkActivity(int workType, Activity activity,BuiltUser userObject){
        this.workType = workType;
        this.activity = activity;
        this.builtUserObject = userObject;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        if(workType == CustomDataClass.LOGIN){
            login();
        }
        else if(workType == CustomDataClass.FETCH_ALBUM){
            fetchData();
        }

        return null;
    }

    private void login(){
        builtUserObject.login("deven@gmail.com", "asdqweasd", new BuiltResultCallBack() {
            boolean rtn;
            // here "john@malkovich.com" is a valid email id of your user
            // and "password", the corresponding password
            @Override
            public void onSuccess() {
                // user has logged in successfully
                String email = builtUserObject.getEmailId();
                String lastName = builtUserObject.getLastName();

                Log.d("Data", "Email id " + email);
                Log.d("Data", "Last Name " + lastName);
                rtn = true;
            }

            @Override
            public void onError(BuiltError builtErrorObject) {
                // login failed
                // the message, code and details of the error
                Log.i("error: ", "" + builtErrorObject.getErrorMessage());
                Log.i("error: ", "" + builtErrorObject.getErrorCode());
                Log.i("error: ", "" + builtErrorObject.getErrors());
                rtn = false;
            }

            @Override
            public void onAlways() {
                ((LoginActivity)activity).updateLogin(rtn);
                // write code here that you want to execute
                // regardless of success or failure of the operation
            }

        });
    }


    private void fetchData(){
        BuiltQuery query = new BuiltQuery("album");

        query.exec(new QueryResultsCallBack() {
            List<BuiltObject> albums;
            @Override
            public void onSuccess(QueryResult queryResultObject) {
                // the queryResultObject will contain the objects of the class
                // here's the object we just created
                albums = queryResultObject.getResultObjects();
                for(BuiltObject object : albums){
                    Log.i("Data","Name "+object.get("name"));
                    Log.i("Data","Title "+object.get("title"));
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
                    ((AlbumActivity)activity).updateData(albums);
                }
            }
        });
    }
}
