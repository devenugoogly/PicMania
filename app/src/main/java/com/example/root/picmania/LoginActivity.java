package com.example.root.picmania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.supportedfiles.CustomDataClass;
import com.example.supportedfiles.NetworkActivity;
import com.raweng.built.Built;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;


public class LoginActivity extends Activity {

    BuiltUser builtUserObject = new BuiltUser();
    boolean rtn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            Built.initializeWithApiKey(LoginActivity.this, "blt643f5d49ff2042cb", "0000011");
        } catch (Exception e) {
            e.printStackTrace();
        }
        login();

    }

    private void login(){
        NetworkActivity loginActivity = new NetworkActivity(CustomDataClass.LOGIN,LoginActivity.this,builtUserObject);
        loginActivity.execute();

//        builtUserObject.login("deven@gmail.com", "asdqweasd", new BuiltResultCallBack() {
//            // here "john@malkovich.com" is a valid email id of your user
//            // and "password", the corresponding password
//            @Override
//            public void onSuccess() {
//                // user has logged in successfully
//                String email = builtUserObject.getEmailId();
//                String lastName = builtUserObject.getLastName();
//
//                Log.d("Data","Email id "+email);
//                Log.d("Data","Last Name "+lastName);
//
//                rtn = true;
//
//            }
//            @Override
//            public void onError(BuiltError builtErrorObject) {
//                // login failed
//                // the message, code and details of the error
//                Log.i("error: ", "" + builtErrorObject.getErrorMessage());
//                Log.i("error: ", "" + builtErrorObject.getErrorCode());
//                Log.i("error: ", "" + builtErrorObject.getErrors());
//                rtn = false;
//            }
//            @Override
//            public void onAlways() {
//                // write code here that you want to execute
//                // regardless of success or failure of the operation
//            }
//
//        });
//
//        Log.i("Return","Value "+rtn);
//        if(rtn){
//            Intent intent = new Intent(this,AlbumActivity.class);
//            startActivity(intent);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void updateLogin(boolean rtn){
        Log.i("Value returned","Value "+rtn);
        if(rtn){
            Intent albumIntent = new Intent(this,AlbumActivity.class);
            startActivity(albumIntent);
        }

    }
}
