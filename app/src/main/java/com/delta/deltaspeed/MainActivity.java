package com.delta.deltaspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    SwitchCompat mSwitchCompat;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitchCompat = findViewById(R.id.switch1);
        mTextView = findViewById(R.id.tv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            doStuff();
        }
        this.updateSpeed(null);
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MainActivity.this.updateSpeed(null);
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Clocation myLocation = new Clocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @SuppressLint("MissingPermission")
    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this, "waiting for gps connection", Toast.LENGTH_SHORT).show();
    }

    private void updateSpeed(Clocation location) {
        float mCurrentSpeer = 0;
        if (location != null) {
            location.setUseMetricUnits(this.useMetricUnits());
            mCurrentSpeer = location.getSpeed();
        }
        

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", mCurrentSpeer);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace("","0");
        if (!this.useMetricUnits()){
            mTextView.setText(strCurrentSpeed+"km/h");
        }
        else{
            mTextView.setText(strCurrentSpeed+"mile/h");
        }
    }

    private boolean useMetricUnits() {
        return mSwitchCompat.isChecked();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else
                finish();
        }
    }
}
