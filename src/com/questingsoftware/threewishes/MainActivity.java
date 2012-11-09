package com.questingsoftware.threewishes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.questingsoftware.threewishes.fragments.CadastrarItemFragment;
import com.questingsoftware.threewishes.fragments.CadastrarItemFragment.CadastroItemCallback;
import com.questingsoftware.threewishes.fragments.ListaItensFragment;
import com.questingsoftware.threewishes.fragments.ListaItensFragment.ListaItemCallback;
import com.questingsoftware.threewishes.service.ConsultaPrecoService;

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
    	menu.findItem(R.id.menu_settings)
    		.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = new Intent(MainActivity.this,PreferenceActivity.class);
					startActivity(intent);
					return true;
				}
			} );
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	activateNotificationService();
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
	
	private void activateNotificationService(){
		Intent intent = new Intent(this, ConsultaPrecoService.class);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 20000, pendingIntent);
	}

}
