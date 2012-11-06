package com.questingsoftware.threewishes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.questingsoftware.threewishes.controller.ListaItensAdapter;
import com.questingsoftware.threewishes.fragments.CadastrarItemFragment;
import com.questingsoftware.threewishes.fragments.ListaItensFragment;
import com.questingsoftware.threewishes.fragments.ListaItensFragment.ListaItemCallback;

public class MainActivity extends FragmentActivity implements ListaItemCallback{
	
	private Fragment fragmentLista;
	private Fragment fragmentForm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState==null){
        	FragmentManager manager = getSupportFragmentManager();
        	fragmentLista = new ListaItensFragment();

        	manager.beginTransaction()
        		//.add(R.id.scrollView, new CadastrarItemFragment())
        		.add(R.id.scrollView, fragmentLista)
        		.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void editItem(int position, String oldValue) {
		FragmentManager manager = getSupportFragmentManager();
		
		if (fragmentForm==null)
			fragmentForm = new CadastrarItemFragment();

    	manager.beginTransaction()
    		.remove(fragmentLista)
    		.add(R.id.scrollView, fragmentForm)
    		.addToBackStack("")
    		.commit();
	}

	@Override
	public void insertNewItem(ListaItensAdapter adapter) {
		// TODO Auto-generated method stub
		
	}
}
