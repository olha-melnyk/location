package ws.bilka.location;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserLocation extends FragmentActivity {

    private static final String TAG = UserLocation.class.getSimpleName();

    private String mUserId;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        mUserId = getIntent().getStringExtra("user_id");
        Log.i(TAG, "User Location: " + mUserId);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            googleMap = fm.getMap();
        }

        if(mUserId != null) {

            Firebase firebase = new Firebase(Constants.FIREBASE_URL).child("users");
            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        ws.bilka.location.Location location = child.child("location").getValue(ws.bilka.location.Location.class);
                        Log.i(TAG, "location = " + location);
                        if (location != null) {
                            Log.i(TAG, mUserId + " location " + location);
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            addMarker(latLng, user.getName());
                        }
                    }

                    DataSnapshot chosenUserLocation = dataSnapshot.child(mUserId).child("location");
                    ws.bilka.location.Location location = chosenUserLocation.getValue(ws.bilka.location.Location.class);

                    if (location != null) {
                        TextView tvLocation = (TextView) findViewById(R.id.tv_location);
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                        tvLocation.setText("Latitude:" + latitude + ", Longitude:" + longitude);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    private void addMarker(LatLng position, String name) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(position).title(name));
        }
    }
}
