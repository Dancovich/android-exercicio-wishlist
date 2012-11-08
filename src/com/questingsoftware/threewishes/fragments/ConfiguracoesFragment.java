package com.questingsoftware.threewishes.fragments;

import com.questingsoftware.threewishes.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfiguracoesFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}
	
	

}
