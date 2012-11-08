package com.questingsoftware.threewishes;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.questingsoftware.threewishes.fragments.ConfiguracoesFragment;

public class PreferenceActivity extends SherlockActivity {
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		getFragmentManager()
			.beginTransaction()
			.add(new ConfiguracoesFragment() , null)
			.commit();
	}

}
