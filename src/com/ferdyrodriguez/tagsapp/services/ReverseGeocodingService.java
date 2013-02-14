package com.ferdyrodriguez.tagsapp.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.ferdyrodriguez.tagsapp.listeners.ReverseGeocodingListener;

public class ReverseGeocodingService extends AsyncTask<Location, Void, Void> {
	
	private final Context mContext;
	private final ReverseGeocodingListener mListener;
	private Address mAddress;


	public ReverseGeocodingService(Context context, ReverseGeocodingListener listener) {
		mContext = context;
		mListener = listener;
	}

	@Override
	protected Void doInBackground(Location... locations) {
		
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		Location loc = locations[0];
		List<Address> addresses = null;
		
		try {
			addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
		} catch (IOException e) {
			mAddress = null;
		}
		
		if (addresses != null && addresses.size() > 0) {
			mAddress = addresses.get(0);
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void aVoid) {
		mListener.onAddressAvailable(mAddress);
	}
	

}
