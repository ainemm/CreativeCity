package com.example.cityview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;



public class ProgressScreen extends Activity {
	
	
	protected LocationManager locationManager;
	
	
	double latitude =0;
	double longitude  =0;
	
	private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
	private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY"; 	
  
    
    GPSTracker gps;
    Location location;
     
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);        
       
        gps = new GPSTracker(ProgressScreen.this);
				
                // check if GPS enabled    
                if(gps.canGetLocation()){		                     
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    saveCoordinatesInPreferences(((float)latitude),((float)longitude));
                    
                    System.out.println("Saved"+latitude + longitude);
                }else{
                    gps.showSettingsAlert();
                }
    }
         
       public void onStart(){
    	   super.onStart();
    	   setContentView(R.layout.activity_main);
    	   	
    	   	Location location = gps.getLocation();  
    	   
   			Location pointLocation = retrievelocationFromPreferences();
   		
   	        float distance = location.distanceTo(pointLocation);
   	        Toast.makeText(ProgressScreen.this, "Distance from Point:"+distance, Toast.LENGTH_LONG).show();
   	        System.out.println("Retrieved"+pointLocation);
   	        System.out.println("Distance from POI"+distance);
   	     
   	     if(distance < 10){

   	    	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
   	    	// Vibrate for 500 milliseconds
   	    	v.vibrate(500);
   	    	
   		            // Starts the About Screen Activity
   	     // Starts the About Screen Activity
            startActivity(new Intent(ProgressScreen.this, AboutScreen.class));  
   	    }
       }
    
    void saveCoordinatesInPreferences(float latitude, float longitude) {
		SharedPreferences prefs = getSharedPreferences("com.project.shared2", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(POINT_LATITUDE_KEY, latitude);
        prefsEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
        prefsEditor.commit();
        Toast.makeText(getApplicationContext(), "Save - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
    }
    
    Location retrievelocationFromPreferences() {
        SharedPreferences prefs = getSharedPreferences("com.project.shared2", MODE_PRIVATE);
        Location location = new Location("POINT_LOCATION");
        location.setLatitude(prefs.getFloat(POINT_LATITUDE_KEY, 0));
        location.setLongitude(prefs.getFloat(POINT_LONGITUDE_KEY, 0));
        return location;       
    }
}


	

		
	
	