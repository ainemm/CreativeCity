package com.example.cityview;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


public class GPSTracker extends Service implements LocationListener {
	
	//distance to update in meters and millisec's
		private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
		private static final long  MIN_TIME_BW_UPDATES = 1000 * 280;
	     
	    	
	    	//GPS & network status
	    	boolean isGPSEnabled = false;
	    	boolean isNetworkEnabled = false;
	    	boolean canGetLocation = false;
	    	
	    	Location location;
	    	double latitude;
	    	double longitude;
	    	
	    	
	    	
	    	//Declaring location Manager
	    	protected LocationManager locationManager;
			private Context mContext;
	    	
	    	public GPSTracker (Context context) {
	    		this.mContext = context;
	    		getLocation();
	    	}
	    	
	    	public Location getLocation(){
	    		try{
	    			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
	    			
	    			//getting GPS & network status 
	    			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    			
	    			if (!isGPSEnabled && !isNetworkEnabled){
	    				//none enabled
	    			}
	    			else{
	    				this.canGetLocation = true;
	    				//first from network provider
	    				if (isNetworkEnabled){
	    					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	    					Log.d("Network","Network");
	    					if(locationManager !=null) {
	    						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    					}
	    					if(location != null) {
	    						latitude = location.getLatitude();
	    						longitude = location.getLongitude();
	    					}
	    				}
	    			}
	    		//Then use GPS service
	    			if (isGPSEnabled){
	    				if(location == null) {
	    					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
	    					Log.d("GPS Enabled", "GPS Enabled");
	    					if(locationManager !=null){
	    						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    						if (location !=null){
	    							latitude = location.getLatitude();
	    							longitude = location.getLongitude();
	    						}
	    					}
	    				}
	    			}
	    	}catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return location;
	    }
	    	
	    
	    // function to get latitude
	    public double getLatitude(){
	    	if(location !=null){
	    		latitude = location.getLatitude();
	    	}
	    	return latitude;
	    }

	    //function to get longitude
	    public double getLongitude(){
	    	if(location !=null){
	    		longitude = location.getLongitude();
	    	}
	    	return longitude;
	    }



	    //Alert message to turn on GPS

	    //check network provider
	    public boolean canGetLocation(){
	    	return this.canGetLocation;
	    }

	    //settings alert box
	    public void showSettingsAlert(){
	    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
	    	alertDialog.setTitle("GPS is settings");
	    	//dialog message
	    	alertDialog.setMessage("GPS are not enabled.Do you want to go to settings menu?");
	    	//on pressing settings button
	    	alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
	    		public void onClick(DialogInterface dialog, int which) {
	    			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    			mContext.startActivity(intent);
	    		}
	    	});
	    	//on pressing cancel button
	    	alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int which){
	    			dialog.cancel();
	    		}
	    	});
	    	
	    	alertDialog.show();
	    }

	    
	  //stop gps listener
	    public void stopUsingGPS(){
	    	if(locationManager !=null){
	    		locationManager.removeUpdates(GPSTracker.this);
	    	}
	    }
	     
	   
/*
		@Override
		public void onLocationChanged(Location location) {
			MainActivity m = new MainActivity();
			Location pointLocation = m.retrievelocationFromPreferences();
			//Location pointLocation = new Location("point b");
			//pointLocation.setLatitude(51.89295038);
			//pointLocation.setLongitude(-8.50112957);
	        float distance = location.distanceTo(pointLocation);
	        Toast.makeText(GPSTracker.this, "Distance from Point:"+distance, Toast.LENGTH_LONG).show();
			
		}*/

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

}
