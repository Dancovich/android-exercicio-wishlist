package com.questingsoftware.threewishes.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

import com.questingsoftware.threewishes.MainActivity;
import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.controller.ListaItensAdapter;
import com.questingsoftware.threewishes.model.WishItem;
import com.questingsoftware.threewishes.persistence.DBOpenHelper;

public class ListaItensFragment extends Fragment {

	private ListaItemCallback callback;
	private ListaItensAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		Log.d(MainActivity.APP_LOG_TAG, activity.getString(R.string.debug_itemlist_attach_activity));

		if (activity instanceof ListaItemCallback) {
			callback = (ListaItemCallback) activity;
		} else {
			callback = null;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ExpandableListView listView = (ExpandableListView) inflater.inflate(
				R.layout.lista_itens, null);

		List<WishItem> lista = DBOpenHelper.selectAll(getActivity());
		
		adapter = new ListaItensAdapter(container.getContext(), lista);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (callback != null) {
					callback.callItemEdit(id);
				}
			}
		});

		return listView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_lista_itens, menu);

		MenuItem menuNew = menu.findItem(R.id.menu_new);
		menuNew.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (callback!=null){
					callback.callItemInsert();
				}
				return true;
			}
		});
	}

	public interface ListaItemCallback {

		public void callItemEdit(Long itemId);

		public void callItemInsert();

	}
}
