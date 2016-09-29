package com.waitandgo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathieu.waitandgo.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.waitandgo.model.MyArrayAdapter;
import com.waitandgo.model.Task;
import com.waitandgo.model.TaskDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;

    private ListView listView;
    private ArrayList<Task> tasks;
    MyArrayAdapter adapter;
    TaskDAO taskDAO;
    private GoogleApiClient mGoogleApiClient;
    private TextView nameTextView, mailTextView;
    private CircleImageView profileImageView;
    private MenuItem signInMenuItem;
    private ProgressDialog mProgressDialog;
    private boolean isConnected = false;
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //display tasks stored in the local database and active listener
        taskDAO = new TaskDAO(this);
        taskDAO.open();
        tasks = taskDAO.getAllTasks();
        adapter = new MyArrayAdapter(this, tasks);
        listView = (ListView) findViewById(R.id.listViewTask);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //Display Sync status of SQLite DB
        Toast.makeText(getApplicationContext(), taskDAO.getSyncStatus(), Toast.LENGTH_LONG).show();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API)
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            int statusCode = result.getStatus().getStatusCode();
            Log.d(TAG, String.valueOf(statusCode) +" : " + result.getStatus());
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            isConnected = true;
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            if (nameTextView != null)
                nameTextView.setText(acct.getDisplayName());
            if (mailTextView != null)
                mailTextView.setText(acct.getEmail());
            if(profileImageView != null){
                if (acct.getPhotoUrl() != null){
                    Uri uri = acct.getPhotoUrl();
                    Picasso.with(this.getApplicationContext())
                            .load(uri)
                            .placeholder(android.R.drawable.sym_def_app_icon)
                            .error(android.R.drawable.sym_def_app_icon)
                            .into(profileImageView);
                }
                else
                    System.out.println("profile image : null");
            }
            if (signInMenuItem != null)
                signInMenuItem.setTitle(getString(R.string.desconectarse));
        }
    }

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        isConnected=false;
                        if (mailTextView != null)
                            mailTextView.setText(getString(R.string.not_connected));
                        if (signInMenuItem != null)
                            signInMenuItem.setTitle(getString(R.string.conectarse));
                        if(profileImageView != null){
                            profileImageView.setImageResource(R.drawable.account);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            if (!isConnected){
                Toast.makeText(getApplicationContext(), "Tienes que estar conectado " +
                        "a tu cuenta google", Toast.LENGTH_LONG).show();
            } else{
                //Sync SQLite DB data to remote MySQL DB
                syncSQLiteMySQLDB();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        mailTextView = (TextView) findViewById(R.id.mailTextView);
        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);

        if (id == R.id.nav_list) {
            // Handle the camera action
        } else if (id == R.id.nav_group) {

        } else if (id == R.id.nav_group_add) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.sign_in){
            signInMenuItem = item;
            if (signInMenuItem.getTitle().equals(getString(R.string.conectarse)))
                signIn();
            else
                signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    /**
     * If an item is clicked it is deleted from the database
     * It is also removed from the ArrayList "tasks" and the view is refreshed
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        for (Iterator<Task> it = tasks.iterator(); it.hasNext();){
            Task itg = (Task) it.next();
            if (itg == (Task) adapterView.getItemAtPosition(position)){
                TaskDAO taskDAO = new TaskDAO(this);
                // taskDAO.open();


                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);

                // Send task data values to EditTaskActivity
                intent.putExtra("TaskId", itg.getId());
                intent.putExtra("TaskTitle", itg.getTitle());
                intent.putExtra("TaskCategory", itg.getCategory());
                intent.putExtra("TaskShareWith", itg.getShareWith());
                intent.putExtra("TaskPrerequisite", itg.getTaskPrerequisite());
                intent.putExtra("TaskDescription", itg.getDescription());

                startActivity(intent);

                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }



    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
        mGoogleApiClient.disconnect();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void syncSQLiteMySQLDB(){
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        tasks = taskDAO.getAllTasks();
        if(tasks.size() != 0){
            if (taskDAO.dbSyncCount() != 0){
                Log.d(TAG, "tasks to sync : "+taskDAO.dbSyncCount());
                this.showProgressDialog();
                params.put("tasksJSON", taskDAO.composeTaskJSONfromSQLite(acct.getEmail(),
                        acct.getDisplayName()));
                //TODO : change the address kdp : 192.168.1.116 ecole : 10.112.12.199
                client.post("http://192.168.1.116/waitandgo/insert_tasks.php",params,
                        new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String response){
                        System.out.println(response);
                        Log.d(TAG," response : " + response);
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.hide();
                        }
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println(jsonArray.length());
                            for(int i=0; i< jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                System.out.println(jsonObject.get("id"));
                                System.out.println(jsonObject.get("status"));
                                taskDAO.updateSyncStatus(jsonObject.get("id").toString(),
                                        jsonObject.get("status").toString());
                            }
                            Toast.makeText(getApplicationContext(), "DB Sync completed", Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error, String content){
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.hide();
                        }
                        if(statusCode == 404){
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        }else if(statusCode == 500){
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        }else{
                            Log.d(TAG,"onFailure : " + String.valueOf(statusCode) + " error : " +error +" content : " +content );
                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data in SQLite DB, please do enter User name to perform Sync action", Toast.LENGTH_LONG).show();
        }
    }
}
