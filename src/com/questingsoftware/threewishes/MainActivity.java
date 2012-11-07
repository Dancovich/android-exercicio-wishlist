package com.questingsoftware.threewishes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.questingsoftware.threewishes.fragments.CadastrarItemFragment;
import com.questingsoftware.threewishes.fragments.CadastrarItemFragment.CadastroItemCallback;
import com.questingsoftware.threewishes.fragments.ListaItensFragment;
import com.questingsoftware.threewishes.fragments.ListaItensFragment.ListaItemCallback;

public class MainActivity extends SherlockFragmentActivity implements ListaItemCallback,CadastroItemCallback{
	
	public static final String APP_LOG_TAG = "ThreeWishesAppTag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState==null){
        	FragmentManager manager = getSupportFragmentManager();
        	
        	manager.beginTransaction()
        		//.add(R.id.scrollView, new CadastrarItemFragment())
        		.add(R.id.scrollView, new ListaItensFragment())
        		.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSherlock().getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void callItemEdit(Long itemId) {
		CadastrarItemFragment fragmentForm = new CadastrarItemFragment();
		Bundle argumentos = new Bundle();
		argumentos.putLong(CadastrarItemFragment.CHAVE_BUNDLE_ID, itemId);
		fragmentForm.setArguments(argumentos);
		
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
			.replace(R.id.scrollView, fragmentForm)
			.addToBackStack(null)
			.commit();
	}

	@Override
	public void callItemInsert() {
		CadastrarItemFragment fragmentForm = new CadastrarItemFragment();
		Bundle argumentos = new Bundle();
		argumentos.putLong(CadastrarItemFragment.CHAVE_BUNDLE_ID, 0L);
		fragmentForm.setArguments(argumentos);
		
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
			.replace(R.id.scrollView, fragmentForm)
			.addToBackStack(null)
			.commit();
	}

	@Override
	public void callItemList() {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStack();
		
		/*manager.beginTransaction()
			.add(new ListaItensFragment(), null)
			.addToBackStack(FRAGMENT_BACKSTACK_START_STATE)
			.commit();*/
	}

}
