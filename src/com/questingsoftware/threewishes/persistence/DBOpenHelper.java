package com.questingsoftware.threewishes.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.questingsoftware.threewishes.model.WishItem;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	private static final int VERSAO = 2;
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
		db.execSQL("create table wishlist (id INTEGER PRIMARY KEY ASC,nome TEXT,categoria TEXT,local TEXT,contato TEXT,precoMinimo REAL,precoMaximo REAL,atualizarPreco INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion<=1){
			db.execSQL("alter table wishlist add column atualizarPreco INTEGER");
		}
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
		
		String atualizarPreco = item.getAtualizarPreco()==null || !item.getAtualizarPreco().booleanValue() ? "0" : "1";
		values.put("atualizarPreco", atualizarPreco);
		
		item.setId(db.insert(TABELA_WISHLIST,null,values));
	}
	
	public static WishItem select(Long id,Context context){
		SQLiteDatabase db = getInstance(context).getReadableDatabase();

		Cursor c = db.rawQuery("select * from "+TABELA_WISHLIST+" where id = ?", new String[]{id.toString()});
		
		WishItem item = null;
		if (c.moveToNext()){
			item = new WishItem();
			item.setId(c.getLong(c.getColumnIndex("id")));
			item.setNome(c.getString(c.getColumnIndex("nome")));
			item.setCategoria(c.getString(c.getColumnIndex("categoria")));
			item.setLocal(c.getString(c.getColumnIndex("local")));
			item.setContato(c.getString(c.getColumnIndex("contato")));
			item.setPrecoMinimo(new BigDecimal(c.getString(c.getColumnIndex("precoMinimo"))));
			item.setPrecoMaximo(new BigDecimal(c.getString(c.getColumnIndex("precoMaximo"))));
			
			int atualizarPreco = c.getInt(c.getColumnIndex("atualizarPreco"));
			item.setAtualizarPreco(atualizarPreco==1);
		}
		
		c.close();
		db.close();
		return item;
	}
	
	public static ArrayList<WishItem> selectAll(Context context){
		SQLiteDatabase db = getInstance(context).getReadableDatabase();

		Cursor c = db.rawQuery("select * from "+TABELA_WISHLIST,null);
		
		WishItem item = null;
		ArrayList<WishItem> retorno = new ArrayList<WishItem>();
		while (c.moveToNext()){
			item = new WishItem();
			item.setId(c.getLong(c.getColumnIndex("id")));
			item.setNome(c.getString(c.getColumnIndex("nome")));
			item.setCategoria(c.getString(c.getColumnIndex("categoria")));
			item.setLocal(c.getString(c.getColumnIndex("local")));
			item.setContato(c.getString(c.getColumnIndex("contato")));
			item.setPrecoMinimo(new BigDecimal(c.getString(c.getColumnIndex("precoMinimo"))));
			item.setPrecoMaximo(new BigDecimal(c.getString(c.getColumnIndex("precoMaximo"))));

			int atualizarPreco = c.getInt(c.getColumnIndex("atualizarPreco"));
			item.setAtualizarPreco(atualizarPreco==1);

			retorno.add(item);
		}
		
		c.close();
		db.close();
		return retorno;
	}

	public static void update(WishItem itemEditado, Context context) {
		SQLiteDatabase db = getInstance(context).getWritableDatabase();
		
		db.beginTransaction();
		ContentValues values = new ContentValues();
		values.put("nome", itemEditado.getNome());
		values.put("categoria", itemEditado.getCategoria());
		values.put("local", itemEditado.getLocal());
		values.put("contato", itemEditado.getContato());
		values.put("precoMinimo", itemEditado.getPrecoMinimo().toString());
		values.put("precoMaximo", itemEditado.getPrecoMaximo().toString());
		
		String atualizarPreco = itemEditado.getAtualizarPreco()==null || !itemEditado.getAtualizarPreco().booleanValue() ? "0" : "1";
		values.put("atualizarPreco", atualizarPreco);
		
		db.update(TABELA_WISHLIST, values, "id=?", new String[]{itemEditado.getId().toString()});
		db.setTransactionSuccessful();
		db.endTransaction();
	}

}
