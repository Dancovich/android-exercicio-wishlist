package com.questingsoftware.threewishes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.questingsoftware.threewishes.fragments.CadastrarItemFragment;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState==null){
        	FragmentManager manager = getSupportFragmentManager();
        	manager.beginTransaction()
        		.add(R.id.mainArea, new CadastrarItemFragment())
        		.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
