package com.ferdyrodriguez.tagsapp;

import android.location.Location;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ferdyrodriguez.tagsapp.listeners.LocationResultListener;
import com.ferdyrodriguez.tagsapp.services.LocationService;

public class CurrentFragment extends SherlockFragment implements LocationResultListener {
	private LocationService mLocationService;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.current_location, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.find_current_location) {
			if (mLocationService == null)
				mLocationService = new LocationService();
			mLocationService.getLocation(getActivity(), this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationResultAvailable(Location location) {
		if (location == null){
			//TODO: assignment
		} else {
			//Reverse geocode location
		}
	}

}
