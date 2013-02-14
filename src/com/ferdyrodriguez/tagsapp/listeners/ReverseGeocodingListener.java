package com.ferdyrodriguez.tagsapp.listeners;

import android.location.Address;

public interface ReverseGeocodingListener {
	public void onAddressAvailable(Address address);
}
