package com.ferdyrodriguez.tagsapp;

import android.content.res.Resources;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ferdyrodriguez.tagsapp.listeners.LocationResultListener;
import com.ferdyrodriguez.tagsapp.listeners.ReverseGeocodingListener;
import com.ferdyrodriguez.tagsapp.services.LocationService;
import com.ferdyrodriguez.tagsapp.services.ReverseGeocodingService;

public class CurrentFragment extends SherlockFragment implements LocationResultListener, ReverseGeocodingListener {
	private LocationService mLocationService;
	private ImageView mLocationIcon;
	private ImageButton mTagButton;
	private TextView mAddress1;
	private TextView mAddress2;
	private TextView mLat;
	private TextView mLon;
	private Address mLastKnownAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.current_frag, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		mLocationIcon = (ImageView) getView().findViewById(R.id.locationIcon);
		mTagButton = (ImageButton) getView().findViewById(R.id.tagButton);
		mAddress1 = (TextView) getView().findViewById(R.id.address1);
		mAddress2 = (TextView) getView().findViewById(R.id.address2);
		mLat = (TextView) getView().findViewById(R.id.latitude);
		mLon = (TextView) getView().findViewById(R.id.longitude);
		
		if (savedInstanceState != null) {
			mLastKnownAddress = savedInstanceState.getParcelable("last_known_address");
		if (mLastKnownAddress != null)
			setAddressDetails(mLastKnownAddress);
		}
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
			new ReverseGeocodingService(getActivity(), this).execute(location);
		}
	}

	@Override
	public void onAddressAvailable(Address address) {
		if (address == null) {
			// TODO: assignment
		} else {
			mLastKnownAddress = address;
			setAddressDetails(address);
		}
		
	}

	private void setAddressDetails(Address address) {
		if (address.getMaxAddressLineIndex() > 0)
			mAddress1.setText(address.getAddressLine(0));
		
		mAddress2.setText(String.format("%s, %s, %s", address.getLocality(), address.getAdminArea(), address.getCountryName()));
		Resources res = getResources();
		mLat.setText(String.format(res.getString(R.string.lat_val), address.getLatitude()));
		mLon.setText(String.format(res.getString(R.string.lon_val), address.getLongitude()));
		
		mTagButton.setImageResource(R.drawable.tag_button);
		mLocationIcon.setImageResource(R.drawable.known_location);
		mTagButton.setClickable(true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (mLastKnownAddress != null)
			outState.putParcelable("last_known_address", mLastKnownAddress);
	}
	
	

}
