package com.questingsoftware.threewishes.fragments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.controller.ListaItensAdapter;
import com.questingsoftware.threewishes.model.WishItem;

public class ListaItensFragment extends Fragment implements OnItemClickListener {
	
	private ListaItemCallback callback;
	private ListaItensAdapter adapter;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof ListaItemCallback){
			callback = (ListaItemCallback)activity;
		}
		else{
			callback = null;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		ExpandableListView listView = (ExpandableListView)inflater.inflate(R.layout.lista_itens, null);
		
		List<WishItem> lista = new ArrayList<WishItem>();
		lista.add(new WishItem("Danilo", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Zé", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Maria", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Rodolfo", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Ronaldo", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Onofre", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Pascoali", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		lista.add(new WishItem("Tássio", "", "Teste", "", new BigDecimal("10.0"), new BigDecimal("900.0")));
		
		adapter = new ListaItensAdapter(container.getContext(),lista);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		return listView;
	}
	
	public interface ListaItemCallback {
		
		public void editItem(int position,String oldValue);
		
		public void insertNewItem(ListaItensAdapter adapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> origin, View item, int position, long id) {
		
	}
}
