package com.ferdyrodriguez.tagsapp;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.ferdyrodriguez.tagsapp.listeners.TabListener;

import android.os.Bundle;



public class DefaultActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		addTabs();
		if(savedInstanceState != null) {
			int index = savedInstanceState.getInt("selected_tab_index", 0);
			getSupportActionBar().setSelectedNavigationItem(index);
		}
	}
	
	private void addTabs() {
		// Setting Navigation mode
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Setting the tabs
		String currentTitle = getResources().getString(R.string.current);
		ActionBar.Tab currentTab = bar.newTab();
		currentTab.setText(currentTitle);
		currentTab.setTabListener(new TabListener(this, currentTitle, CurrentFragment.class));
		bar.addTab(currentTab);
		
		String locationsTitle = getResources().getString(R.string.locations);
		ActionBar.Tab locationsTab = bar.newTab();
		locationsTab.setText(locationsTitle);
		locationsTab.setTabListener(new TabListener(this, locationsTitle, LocationsFragment.class));
		bar.addTab(locationsTab);
		
		String mapTitle = getResources().getString(R.string.map);
		ActionBar.Tab mapTab = bar.newTab();
		mapTab.setText(mapTitle);
		mapTab.setTabListener(new TabListener(this, mapTitle, MapFragment.class));
		bar.addTab(mapTab);
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Saving tab selected setting when there a change in app config.
		int index = getSupportActionBar().getSelectedNavigationIndex();
		outState.putInt("selected_tab_index", index);
	}
	
	
}
