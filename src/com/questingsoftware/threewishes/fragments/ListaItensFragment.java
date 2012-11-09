package com.questingsoftware.threewishes.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.questingsoftware.threewishes.MainActivity;
import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.controller.ListaItensAdapter;
import com.questingsoftware.threewishes.model.WishItem;
import com.questingsoftware.threewishes.persistence.DBOpenHelper;

public class ListaItensFragment extends SherlockFragment {

	private ListaItemCallback callback;
	private ListaItensAdapter adapter;
	private ListaItensSelectCallback listaItensSelectCallback;
	private ActionMode actionMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		Log.d(MainActivity.APP_LOG_TAG,
				activity.getString(R.string.debug_itemlist_attach_activity));

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

		/*
		 * Anexa uma action bar contextual que aparece ao segurar um item na
		 * tela. Esta action bar contextual conterá ações como editar e excluir
		 * o item selecionado.
		 */
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (actionMode != null) {
					return false;
				}

				if (listaItensSelectCallback == null) {
					listaItensSelectCallback = new ListaItensSelectCallback();
				}

				WishItem item = (WishItem) parent.getItemAtPosition(position);
				listaItensSelectCallback.setSelectedItem(item);
				actionMode = getSherlockActivity().startActionMode(
						listaItensSelectCallback);
				return true;
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
				if (callback != null) {
					callback.callItemInsert();
				}
				return true;
			}
		});
	}

	/**
	 * Classes {@link Activity} que implementam este callback podem receber
	 * solicitações deste fragment para executar ações.
	 * 
	 */
	public interface ListaItemCallback {

		public void callItemEdit(Long itemId);

		public void callItemInsert();

	}

	/**
	 * Classe que gerencia as ações contextuais executadas em cima de itens
	 * selecionados pelo usuário.
	 * 
	 * 
	 */
	private class ListaItensSelectCallback implements ActionMode.Callback {

		private WishItem selectedItem;

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.getMenuInflater().inflate(R.menu.actionmode_lista_itens, menu);
			menu.findItem(R.id.itemCheckPrice).setChecked(selectedItem.getAtualizarPreco());
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.itemEdit:
				mode.finish();

				if (callback != null) {
					Log.d(MainActivity.APP_LOG_TAG,
							ListaItensFragment.this.getSherlockActivity()
									.getString(R.string.debug_item_edit));
					callback.callItemEdit(selectedItem.getId());
				}

				return true;

			case R.id.itemCheckPrice:
				item.setChecked( !item.isChecked() );
				selectedItem.setAtualizarPreco(item.isChecked());
				DBOpenHelper.update(selectedItem, getSherlockActivity());
				mode.finish();
				return true;
			}

			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			actionMode = null;
		}

		public void setSelectedItem(WishItem selectedItem) {
			this.selectedItem = selectedItem;
		}

		

	}
}
