package app.atti.Activities.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import app.atti.R;
import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, PlacesListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private com.google.android.gms.maps.GoogleMap mMap;

    double mlat;
    double mlng;
    LinearLayout tab;
    private GpsInfo gpsinfo;

    RadioGroup radioGroup;
    RadioButton rd1, rd2, rd3;

    LatLng currentPosition;
    Marker current_marker = null;
    List<Marker> previous_marker = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        rd1 = findViewById(R.id.radio1);
        rd2 = findViewById(R.id.radio2);
        rd3 = findViewById(R.id.radio3);

        radioGroup = findViewById(R.id.radiogroup);
        previous_marker = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_map);
        mapFragment.getMapAsync(this);

        tab = findViewById(R.id.tab);
        tab.bringToFront();
        rd1.bringToFront();
        rd2.bringToFront();
        rd3.bringToFront();
        radioGroup.bringToFront();

        gpsinfo = new GpsInfo(this);
        mlat = gpsinfo.getLatitude();
        mlng = gpsinfo.getLongitude();

        Log.e("GoogleMapCreated", "OnComplete");


        Log.e("GPS ON?", ((LocationManager) this.getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER) + "");

        rd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                ShowPlaces(PlaceType.CONVENIENCE_STORE);
            }
        });

        rd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                ShowPlaces(PlaceType.DOCTOR);
                ShowPlaces(PlaceType.HOSPITAL);
            }
        });
        rd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                ShowPlaces(PlaceType.PHARMACY);
            }
        });
    }

    private void ShowPlaces(String placeType) {
        new NRPlaces.Builder()
                .listener(MapActivity.this)
                .key("AIzaSyD5EzVJTG9migGbdHiH8rj7PqfsILuW1nE")
                .latlng(mlat, mlng)
                .radius(500)
                .type(placeType)
                .build()
                .execute();
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        showDefaultLocation();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(13);
        enableMyLocationIfPermitted();

        if (gpsinfo.isGPSEnabled) {
            Log.e("lat lng", mlat + " " + mlng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mlat, mlng), 17));
        }
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
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };


    private GoogleApiClient mGoogleApiClient = null;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private LocationRequest mLocationRequest;
    LocationManager locationManager;
    boolean setGPS = false;

    @Override

    public void onConnected(@Nullable Bundle bundle) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setGPS = true;
            enableMyLocationIfPermitted();
        }

        mLocationRequest = new LocationRequest();
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (!mGoogleApiClient.isConnected()) mGoogleApiClient.connect();
            // LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
            if (setGPS && mGoogleApiClient.isConnected())//|| locationAvailability.isLocationAvailable() )
            {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (location == null) return;
            }
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("asd", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        //구글 플레이 서비스 연결이 해제되었을 때, 재연결 시도
        Log.d("asd", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("asd", "OnStart");

        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("asd", "OnResume");

        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.unregisterConnectionFailedListener(this);
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mlat=location.getLatitude();
        mlng=location.getLongitude();

        String errorMessage = "";
        if (current_marker != null)
            current_marker.remove();
        //현재 위치에 마커 생성
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("현재위치");
        current_marker = mMap.addMarker(markerOptions);
        Log.d("asd", "OnStart");

        //지도 상에서 보여주는 영역 이동
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setCompassEnabled(true);

        if(rd1.isChecked()){
            mMap.clear();
            ShowPlaces(PlaceType.CONVENIENCE_STORE);
        }else if(rd2.isChecked()){
            mMap.clear();
            ShowPlaces(PlaceType.HOSPITAL);
            ShowPlaces(PlaceType.DOCTOR);
        }else if(rd3.isChecked()){
            mMap.clear();
            ShowPlaces(PlaceType.PHARMACY);
        }

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // Address found using the Geocoder.
        List<Address> addresses = null;
        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, we get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "지오코더 서비스 사용불가";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "잘못된 GPS 좌표";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "주소 미발견";
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Address address = addresses.get(0);
            Toast.makeText(this, address.getAddressLine(0).toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPlacesFailure(PlacesException e) {
        Log.i("PlacesAPI", "onPlacesFailure()");
    }

    @Override
    public void onPlacesStart() {
        Log.i("PlacesAPI", "onPlacesStart()");
    }

    @Override
    public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
        Log.i("PlacesAPI", "onPlacesSuccess()");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {
                    LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(place.getVicinity());
                    Marker item = mMap.addMarker(markerOptions);
                    previous_marker.add(item);
                    //중복 마커 제거
                    HashSet<Marker> hashSet = new HashSet<Marker>();
                    hashSet.addAll(previous_marker);
                    previous_marker.clear();
                    previous_marker.addAll(hashSet);
                }
            }
        });
    }

    @Override
    public void onPlacesFinished() {
        Log.i("PlacesAPI", "onPlacesFinished()");
    }

}
