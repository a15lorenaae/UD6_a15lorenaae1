package com.example.a15lorenaae.ud6_a15lorenaae;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class XeoLocalizacion extends Activity implements LocationListener {

    private static  ArrayList<String> localizacions;
    private static ArrayAdapter<String> adaptador;

    private LocationManager locManager;
    private String provedor;

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
                        XeoLocalizacion.this.finish();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void xestionarEventos(){


                locManager.requestLocationUpdates(provedor,0,100,XeoLocalizacion.this);
                Toast.makeText(getApplicationContext(), "Comenzado a rexistrar...", Toast.LENGTH_SHORT).show();

                Location last =locManager.getLastKnownLocation(provedor);
                if (last!=null)
                    localizacions.add("ULTIMA COÑECIDA: LAT:" + String.valueOf(last.getLatitude()) + " - LONX:" + String.valueOf(last.getLongitude()));




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ud6_a15lorenaae);
        obterprovedores();
        xestionarEventos();
    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        localizacions.add("LATITUDE:" + String.valueOf(location.getLatitude()) + " - LONXITUDE:" + String.valueOf(location.getLongitude()));
        adaptador.notifyDataSetChanged();
    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "O provedor " + provider + " xa non está activo", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "O provedor " + provider + " está activo", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}