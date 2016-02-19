package com.example.a15lorenaae.ud6_a15lorenaae;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UD6_a15lorenaae extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    Marker marcaactual;
    private LocationManager locationManager;
    private static String proveedor;

    private void obterproveedores() {
        Criteria filtro = new Criteria();
        filtro.setAccuracy(Criteria.ACCURACY_FINE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        proveedor = locationManager.getBestProvider(filtro, false);

        if (proveedor == null) {
            Toast.makeText(getApplicationContext(), "Non existen proveedores dispoñibles", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!locationManager.isProviderEnabled(proveedor)) {
            Toast.makeText(getApplicationContext(), "0 " + proveedor + " non esta activo", Toast.LENGTH_SHORT).show();
                dialogoAlertaNonGPS();
        } else {
            Toast.makeText(getApplicationContext(), "proveedor atopado:" + proveedor, Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogoAlertaNonGPS() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(
            "O GPS parece desactivado, queres activalo ?")
            .setCancelable(false)
    .setPositiveButton("Si",
                               new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            // TODO Auto-generated method stub
            startActivity(new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        }
    })
            .setNegativeButton("Non", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                    UD6_a15lorenaae.this.finish();

                }
            });
    final AlertDialog alert = builder.create();
    alert.show();
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ud6_a15lorenaae);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(UD6_a15lorenaae.this);
        obterproveedores();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 500, this);


        LatLng pos=new LatLng(42.879985, -8.544855);
        marcaactual=googleMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("Lore")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.lazo)).snippet("Estamos en: " +pos.toString()));



    }


    @Override
    public void onLocationChanged(Location location) {

        mMap.clear();
        LatLng posicion  = new LatLng(location.getLatitude(),location.getLongitude());
        marcaactual=mMap.addMarker(new MarkerOptions()
                .position(posicion)
                .title("Lore").icon(BitmapDescriptorFactory.fromResource(R.mipmap.lazo)).snippet("Estamos en " + posicion.toString()));
                 location=locationManager.getLastKnownLocation(UD6_a15lorenaae.proveedor);
       //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcaactual.getPosition(),20));
        if (location!=null) {
            Toast.makeText(getApplicationContext(), "Ahora estamos en: " + posicion.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getApplicationContext(), "O provedor " + provider + " cambió  de estado:" + String.valueOf(status), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(),"O proveedor "+provider+" esta activo",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(),"O proveedor "+provider+" xa non esta activo",Toast.LENGTH_SHORT).show();


    }
}
