package activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iamhappy.in.iamhappy.R;
import server.GsonRequest;
import util.ConstantUtils;
import vo.PhotosVO;


public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainFragment";
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        loginButton.setVisibility(View.GONE);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email", "user_photos");

        if(isLoggedIn()){
           // Fetch results from server

        }
        else{
            loginButton.setVisibility(View.VISIBLE);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //callback registration

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                AccessToken accessToken = loginResult.getAccessToken();

                                GraphRequest request = GraphRequest.newMeRequest(
                                        accessToken,
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {
                                                Bundle parameters = new Bundle();
                                                parameters.putString("fields", "source");

                                                try {
                                                    new GraphRequest(
                                                            AccessToken.getCurrentAccessToken(),
                                                            "/"+response.getJSONObject().getString("id")+"/photos",
                                                            parameters,
                                                            HttpMethod.GET,
                                                            new GraphRequest.Callback() {
                                                                public void onCompleted(GraphResponse response) {
                                                                    try {
                                                                        Gson gson = new Gson();
                                                                        JSONObject jsonObject = new JSONObject(response.getRawResponse());
                                                                        String photoJson =jsonObject.get("data").toString();
                                                                        Type type = new TypeToken<ArrayList<PhotosVO>>(){}.getType();
                                                                        ArrayList<PhotosVO> photosVOs = gson.fromJson(photoJson, type);
                                                                        Intent intent = new Intent(MainActivity.this,TimelineActivity.class);
                                                                        intent.putParcelableArrayListExtra(ConstantUtils.IKEY_FEED_LIST, photosVOs);
                                                                        startActivity(intent);
                                                                    }
                                                                    catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                    ).executeAsync();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,email,name");
                                request.setParameters(parameters);
                                request.executeAsync();
//                                Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancel() {
                                // App code
                                Toast.makeText(getApplication(), "fail", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                Toast.makeText(getApplication(), "error", Toast.LENGTH_SHORT).show();
                            }
                        });
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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
