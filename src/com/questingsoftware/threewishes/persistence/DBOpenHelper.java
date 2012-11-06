package com.questingsoftware.threewishes.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.questingsoftware.threewishes.model.WishItem;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	private static final int VERSAO = 1;
	private static final String ARQUIVO_DB = "wishlist.db";
	private static final String TABELA_WISHLIST = "wishlist";
	
	private static DBOpenHelper instance;

	private DBOpenHelper(Context context) {
		super(context, ARQUIVO_DB, null, VERSAO);
	}
	
	private static DBOpenHelper getInstance(Context context){
		if (instance==null){
			instance = new DBOpenHelper(context);
		}
		
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table wishlist (id INTEGER PRIMARY KEY ASC,nome TEXT,categoria TEXT,local TEXT,contato TEXT,precoMinimo REAL,precoMaximo REAL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public static void insert(WishItem item,Context context){
		SQLiteDatabase db = getInstance(context).getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("nome", item.getNome());
		values.put("categoria", item.getCategoria());
		values.put("local", item.getLocal());
		values.put("contato", item.getContato());
		values.put("precoMinimo", item.getPrecoMinimo().toString());
		values.put("precoMaximo", item.getPrecoMaximo().toString());
		
		item.setId(db.insert(TABELA_WISHLIST,null,values));
	}

}
