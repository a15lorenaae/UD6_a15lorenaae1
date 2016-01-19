package com.example.a15lorenaae.ud6_a15lorenaae;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UD6_a15lorenaae extends FragmentActivity implements OnMapReadyCallback {
    private LocationManager locManager;
    private String provedor;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ud6_a15lorenaae);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    private void obterprovedores(){
        Criteria filtro = new Criteria();
        filtro.setAccuracy(Criteria.ACCURACY_FINE);

        locManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        provedor = locManager.getBestProvider(filtro, false);   // Se non está activo o avisamos e chamamos a activity para activalo
//              provedor = LocationManager.NETWORK_PROVIDER;    => Exemplo concreto sen filtro

        if (provedor==null){
            Toast.makeText(getApplicationContext(), "Non existen provedores dispoñibles.", Toast.LENGTH_LONG).show();
            finish();
        }
        if (!locManager.isProviderEnabled(provedor)){
            Toast.makeText(getApplicationContext(), "O " + provedor + " non está activo", Toast.LENGTH_LONG).show();
            dialogoAlertaNonGPS();
        }
        else{
            Toast.makeText(getApplicationContext(), "provedor atopado:" + provedor, Toast.LENGTH_LONG).show();
        }
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(XeoLocalizacion.localizacions).title("Lore").snippet("Estamos nesta posicion "+googleMap.getCameraPosition().target.toString()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.lazo)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));




    }
}
