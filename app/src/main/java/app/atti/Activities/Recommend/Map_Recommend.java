package app.atti.Activities.Recommend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import app.atti.Activities.Map.GpsInfo;
import app.atti.R;

public class Map_Recommend extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private com.google.android.gms.maps.GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    double mlat;
    double mlng;

    LatLng currentPosition;
    Marker current_marker = null;
    Marker marker = null;
    private GpsInfo gpsinfo;
    String rec_loc;
    String rec_loc_name;

    double nlat, nlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__recommend);

        gpsinfo = new GpsInfo(this);

        Intent intent = getIntent();
        rec_loc = intent.getStringExtra("rec_loc");
        rec_loc_name=intent.getStringExtra("rec_loc_name");

        Geocoder geocoder = new Geocoder(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_rec);
        mapFragment.getMapAsync(this);

        List<Address> list = null;


        try {
            list = geocoder.getFromLocationName(rec_loc, 10);
            nlng = list.get(0).getLongitude();
            nlat = list.get(0).getLatitude();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }


    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(13);
        enableMyLocationIfPermitted();

        mlat = gpsinfo.getLatitude();
        mlng = gpsinfo.getLongitude();

        if (gpsinfo.isGPSEnabled) {
            Log.e("lat lng", mlat + " " + mlng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlat, mlng), 17));
        }else{
            showDefaultLocation();
        }

        LatLng latLng = new LatLng(nlat, nlng);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(rec_loc_name);
        marker = mMap.addMarker(markerOption);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(nlat,nlng)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);

        }
    }

    private void showDefaultLocation() {
        LatLng redmond = new LatLng(37.542766, 126.967056);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(13);
                    return false;
                }
            };

    @Override
    public void onLocationChanged(Location location) {
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mlat = location.getLatitude();
        mlng = location.getLongitude();

        if (current_marker != null)
            current_marker.remove();
        //현재 위치에 마커 생성
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("현재위치");
        current_marker = mMap.addMarker(markerOptions);

        //지도 상에서 보여주는 영역 이동
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setCompassEnabled(true);
    }
}
