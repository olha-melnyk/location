package ws.bilka.location;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    LocalActivityManager mlam;
    private String mUsername;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTabs(savedInstanceState);



        mUsername = Constants.FIREBASE_URL + "/users/" + mUserId + "/location";

        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        new Firebase(mUsername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add((String) dataSnapshot.child("title").getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove((String) dataSnapshot.child("title").getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void initTabs(Bundle savedInstanceState) {

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        mlam = new LocalActivityManager(this, false);
        mlam.dispatchCreate(savedInstanceState);
        tabHost.setup(mlam);

        final TabHost.TabSpec tab1 = tabHost.newTabSpec(getString(R.string.friends));
        tab1.setIndicator(getString(R.string.friends));
        tab1.setContent(new Intent(this, FriendsActivity.class));
        tabHost.addTab(tab1);

        final TabHost.TabSpec tab2 = tabHost.newTabSpec(getString(R.string.map));
        tab2.setIndicator(getString(R.string.map));
        tab2.setContent(new Intent(MainActivity.this, MapsActivity.class));
        tabHost.addTab(tab2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

