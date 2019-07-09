package app.atti.Activities.Map;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;

import app.atti.R;

public class MapActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private com.google.android.gms.maps.GoogleMap mMap;

    double mlat;
    double mlng;

    private GpsInfo gpsinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        gpsinfo = new GpsInfo(this);
        mlat = gpsinfo.getLatitude();
        mlng = gpsinfo.getLongitude();

        Log.e("GoogleMapCreated", "OnComplete");

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_map);
//        mapFragment.getMapAsync(this);
    }
}
