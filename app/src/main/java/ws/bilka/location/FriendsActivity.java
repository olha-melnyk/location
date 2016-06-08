package ws.bilka.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class FriendsActivity extends Activity {

    private static final String TAG = FriendsActivity.class.getSimpleName();

    private List<User> mUsers = new LinkedList<>();

    private String mUserId;
    private String mUsername;
    private Firebase mRef;

    private UserListAdapter mUserListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mRef = new Firebase(Constants.FIREBASE_URL);
        if (mRef.getAuth() == null) {
            loadLoginView();
        }

        try {
            mUserId = mRef.getAuth().getUid();
        } catch (Exception e) {
            loadLoginView();
        }

        mUsername = Constants.FIREBASE_URL + "/user/" + mUserId + "/name";
        ListView listView = (ListView) findViewById(R.id.friendsList);
        mUserListAdapter = new UserListAdapter(this, mUsers);
        listView.setAdapter(mUserListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), UserLocation.class);
                User user = mUsers.get(position);
                intent.putExtra("user_id", user.getId());
                startActivity(intent);
            }
        });

        Firebase.setAndroidContext(this);
        loadUsers();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void loadUsers() {

        final Firebase firebase = new Firebase(Constants.FIREBASE_URL).child("users");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {
                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    Log.i(TAG, ">> " + userSnapshot);
                    User user = userSnapshot.getValue(User.class);
                    firebase.child(userSnapshot.getKey()).setValue(user);
                    mUsers.add(user);
                }
                mUserListAdapter.update();
                Log.i(TAG, "data changed " + usersSnapshot.getValue());
                Log.i(TAG, "data changed Num of friends" + usersSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "cancelled");
            }
        });
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
