package com.questingsoftware.threewishes.controller;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.model.WishItem;

public class ListaItensAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private List<WishItem> dados;
	private DecimalFormat format = new DecimalFormat("0.00");
	
	public ListaItensAdapter(Context context, List<WishItem> dados) {
		super();
		this.context = context;
		this.dados = dados;
	}
	
	@Override
	public Object getChild(int groupPos, int childPos) {
		final WishItem item = dados.get(groupPos);
		return new String[]{
				item.getCategoria()
				,format.format(item.getPrecoMinimo())
				,format.format(item.getPrecoMaximo())
		};
	}
	
	@Override
	public long getChildId(int groupPos, int childPos) {
		return childPos;
	}
	
	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
			ViewGroup parentView) {
		
		if (convertView==null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.linha_subitem, null);
		}

		String[] dadosSubitem = (String[]) getChild(groupPos, childPos);
		((TextView)convertView.findViewById(R.id.textCategory)).setText(dadosSubitem[0]);
		((TextView)convertView.findViewById(R.id.textMinPrice)).setText(dadosSubitem[1]);
		((TextView)convertView.findViewById(R.id.textMaxPrice)).setText(dadosSubitem[2]);

		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPos) {
		return 1;
	}
	@Override
	public Object getGroup(int groupPos) {
		return dados.get(groupPos);
	}
	@Override
	public int getGroupCount() {
		return dados!=null ? dados.size() : 0;
	}
	@Override
	public long getGroupId(int groupPos) {
		return ((WishItem)getGroup(groupPos)).getId();
	}
	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parentView) {
		
		if (convertView==null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.linha_item, null);
		}
		
		TextView text = (TextView) convertView.findViewById(R.id.listItemDescription);
		text.setText( ((WishItem)getGroup(groupPos)).getNome() );
		
		return convertView;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
	@Override
	public boolean isChildSelectable(int groupPos, int childPos) {
		return false;
	}
	
	

}
