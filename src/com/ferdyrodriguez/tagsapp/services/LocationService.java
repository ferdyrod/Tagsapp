package com.ferdyrodriguez.tagsapp.services;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.ferdyrodriguez.tagsapp.listeners.LocationResultListener;

public class LocationService {
	private final LocationListener mGPSLocationListener;
	private final LocationListener mNetworkLocationListener;
	private LocationResultListener mLocationResultListener;
	private LocationManager mLocationManager;
	private Timer mTimer;
	private boolean mGPSEnabled;
	private boolean mNetworkEnabled;
	
	public LocationService() {
		// GPS Location Listener
		mGPSLocationListener = new LocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				mTimer.cancel();
				mLocationManager.removeUpdates(this);
				mLocationManager.removeUpdates(mNetworkLocationListener);
				mLocationResultListener.onLocationResultAvailable(location);
			}
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		mNetworkLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				mTimer.cancel();
				mLocationManager.removeUpdates(this);
				mLocationManager.removeUpdates(mGPSLocationListener);
				mLocationResultListener.onLocationResultAvailable(location);
				
			}
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

	public boolean getLocation(Context context, LocationResultListener locationListener) {
		mLocationResultListener = locationListener;
		if (mLocationManager == null)
			mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		try {
			mGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {}
		
		
		try {
			mNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {}
		
		if (!mGPSEnabled && !mNetworkEnabled)
			// Aqui se abre los settings para que configure un Provider
			return false;
		
		if (mGPSEnabled)
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGPSLocationListener);
		
		if (mNetworkEnabled)
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mNetworkLocationListener);
		
		mTimer = new Timer();
		mTimer.schedule(new LastLocationFetcher(), 20000);
		return true;
	}
	
	private class LastLocationFetcher extends TimerTask {

		@Override
		public void run() {

			//remove GPS Location Listener
			mLocationManager.removeUpdates(mGPSLocationListener);
			
			//remove Networkd Location Listener
			mLocationManager.removeUpdates(mNetworkLocationListener);
			
			Location gpsLoc = null, netLoc = null;
			
			if (mGPSEnabled)
				gpsLoc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			if (mNetworkEnabled)
				netLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			if (gpsLoc != null  && netLoc != null) {
				if (gpsLoc.getTime() > netLoc.getTime())
					mLocationResultListener.onLocationResultAvailable(gpsLoc);
				else
					mLocationResultListener.onLocationResultAvailable(netLoc);
				return;
			}
			
			if (gpsLoc != null) {
				mLocationResultListener.onLocationResultAvailable(gpsLoc);
				return;
			}
			
			if (netLoc != null) {
				mLocationResultListener.onLocationResultAvailable(netLoc);
				return;
			}
			
			mLocationResultListener.onLocationResultAvailable(null);
			
		}
		
	}

}
