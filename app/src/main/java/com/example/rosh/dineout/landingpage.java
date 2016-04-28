package com.example.rosh.dineout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.OnClick;

public class landingpage extends AppCompatActivity {
    private Button btnloc;
    private TextView txtOutput;
    private LocationListener Loclistener;
    private LocationManager LocManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        btnloc = (Button) findViewById(R.id.btn_loc);
        txtOutput=(TextView)findViewById(R.id.txtoutput);

        LocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Loclistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                txtOutput.append("\n"+location.getLatitude()+" "+location.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               requestPermissions(new String[]{
                       Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
               },10);
                return;
            }
        }else{
            configurebutton();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
         switch (requestCode){
             case 10:
                 if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configurebutton();
                 return;


         }
    }

    private void configurebutton() {

            btnloc.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    LocManager.requestLocationUpdates("gps", 5000, 0, Loclistener);
                }


            });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landingpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
