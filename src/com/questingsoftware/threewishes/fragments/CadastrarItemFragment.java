package com.questingsoftware.threewishes.fragments;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.questingsoftware.threewishes.MainActivity;
import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.model.WishItem;
import com.questingsoftware.threewishes.net.HttpUtil;
import com.questingsoftware.threewishes.persistence.DBOpenHelper;

public class CadastrarItemFragment extends SherlockFragment implements
		OnMenuItemClickListener {

	private CadastroItemCallback callback;

	private WishItem itemEditado;

	private EditText editNome, editLocal, editCategoria, editContato,
			editPrecoMinimo, editPrecoMaximo;

	public static final String CHAVE_BUNDLE_ID = "idItem";

	/**
	 * Use este método para informar esta tela com um item para editar. Informe
	 * o ID como argumento para o bundle usando a chave
	 * {@link CadastrarItemFragment#CHAVE_BUNDLE_ID}. Passar 0L como valor deste
	 * argumento limpa o formulário para criação de um novo item.
	 */
	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);

		long itemId = args.getLong(CHAVE_BUNDLE_ID);
		if (itemId != 0L) {
			WishItem item = DBOpenHelper.select(itemId, getActivity());
			if (item != null) {
				itemEditado = item;
			} else {
				throw new NullPointerException(getActivity().getString(
						R.string.null_item_edit));
			}
		} else {
			itemEditado = new WishItem();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if (savedInstanceState != null) {
			copyBundleToItem(savedInstanceState);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View returnView = inflater.inflate(R.layout.cadastro_item, null);

		editNome = (EditText) returnView.findViewById(R.id.editItemName);
		editLocal = (EditText) returnView
				.findViewById(R.id.editItemGeoposition);
		editCategoria = (EditText) returnView
				.findViewById(R.id.editItemCategory);
		editContato = (EditText) returnView.findViewById(R.id.editContact);
		editPrecoMinimo = (EditText) returnView.findViewById(R.id.editMinPrice);
		editPrecoMaximo = (EditText) returnView.findViewById(R.id.editMaxPrice);

		// Anexa ações aos botões da tela
		Button botaoBuscape = (Button) returnView
				.findViewById(R.id.buttonExternal);
		botaoBuscape.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				new AsyncTask<String, Void, String>() {
					
					@Override
					protected String doInBackground(String... params) {
						String productDescriptionText = null;
						
						Log.d(MainActivity.APP_LOG_TAG,getSherlockActivity().getString(
								R.string.debug_json_communication));
						
						String json = HttpUtil.doGet(params[0]);
						if (json!=null){
							try {
								JSONObject jsonObject = new JSONObject(json);
								JSONArray jsonArray = jsonObject.getJSONArray("product");
								
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject productObject = jsonArray.getJSONObject(i);
									JSONObject productDescription = productObject.getJSONObject("product");
									productDescriptionText = productDescription.getString("productname");
								}
								
							} catch (JSONException e) {
								productDescriptionText = null;
								e.printStackTrace();
							}
						}

						return productDescriptionText;
					}

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);

						if (editCategoria!=null){
							editCategoria.setText(result);
						}
					}
				}.execute("http://sandbox.buscape.com/service/findProductList/72577349624e6c685068513d/?keyword=keyword&format=json");
			}
		});

		copyItemToForm();

		return returnView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_cadastro_item, menu);

		menu.findItem(R.id.menu_save).setOnMenuItemClickListener(this);
		menu.findItem(R.id.menu_cancel).setOnMenuItemClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		Log.d(MainActivity.APP_LOG_TAG,
				activity.getString(R.string.debug_newitem_attach_activity));

		if (activity instanceof CadastroItemCallback) {
			callback = (CadastroItemCallback) activity;
		} else {
			callback = null;
		}
	}

	private void copyFormToItem() {
		itemEditado.setNome(editNome.getText().toString());
		itemEditado.setCategoria(editCategoria.getText().toString());
		itemEditado.setLocal(editLocal.getText().toString());
		itemEditado.setContato(editContato.getText().toString());
		itemEditado.setPrecoMinimo(new BigDecimal(editPrecoMinimo.getText()
				.toString()));
		itemEditado.setPrecoMaximo(new BigDecimal(editPrecoMaximo.getText()
				.toString()));
	}

	private void copyItemToForm() {
		editNome.setText(itemEditado.getNome());
		editCategoria.setText(itemEditado.getCategoria());
		editLocal.setText(itemEditado.getLocal());
		editContato.setText(itemEditado.getContato());
		editPrecoMinimo
				.setText(itemEditado.getPrecoMinimo() != null ? itemEditado
						.getPrecoMinimo().toString() : "");
		editPrecoMaximo
				.setText(itemEditado.getPrecoMaximo() != null ? itemEditado
						.getPrecoMaximo().toString() : "");
	}

	/**
	 * Chamado quando a aplicação será destruída para uma mudança de
	 * configuração (ex: giro de tela). Salva os dados já informados pelo
	 * usuário para que após a reconstrução da tela o usuário possa continuar de
	 * onde ele parou
	 * 
	 */
	private void copyItemToBundle(Bundle bundle) {

		if (itemEditado.getId() != null) {
			bundle.putLong("id", itemEditado.getId());
		}

		bundle.putString("nome", itemEditado.getNome());
		bundle.putString("categoria", itemEditado.getCategoria());
		bundle.putString("local", itemEditado.getLocal());
		bundle.putString("contato", itemEditado.getContato());
		bundle.putString("precoMinimo",
				itemEditado.getPrecoMinimo() != null ? itemEditado
						.getPrecoMinimo().toString() : "");
		bundle.putString("precoMaximo",
				itemEditado.getPrecoMaximo() != null ? itemEditado
						.getPrecoMaximo().toString() : "");
	}

	/**
	 * Chamado após restaurar de uma mudança de configuração (ex: giro de tela).
	 * Pega os dados salvos no bundle para restaurar o item que estava sendo
	 * inserido/editado sem perda de dados para o usuário
	 */
	private void copyBundleToItem(Bundle bundle) {
		if (itemEditado == null) {
			itemEditado = new WishItem();
		}

		itemEditado.setId(bundle.getLong("id"));
		itemEditado.setNome(bundle.getString("nome"));
		itemEditado.setCategoria(bundle.getString("categoria"));
		itemEditado.setLocal(bundle.getString("local"));
		itemEditado.setContato(bundle.getString("contato"));

		String strPrecoMinimo = bundle.getString("precoMinimo");
		if (strPrecoMinimo != null && strPrecoMinimo.length() > 0) {
			itemEditado.setPrecoMinimo(new BigDecimal(strPrecoMinimo));
		}

		String strPrecoMaximo = bundle.getString("precoMaximo");
		if (strPrecoMaximo != null && strPrecoMaximo.length() > 0) {
			itemEditado.setPrecoMaximo(new BigDecimal(strPrecoMaximo));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		copyItemToBundle(outState);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_save:
			// Apertada opção salvar
			copyFormToItem();

			if (itemEditado.getId() == null || itemEditado.getId() == 0) {
				itemEditado.setId(null);
				DBOpenHelper.insert(itemEditado, getActivity());
			} else {
				DBOpenHelper.update(itemEditado, getActivity());
			}

			if (callback != null) {
				callback.callItemList();
			}
			return true;
			
		case R.id.menu_cancel:
			//Apertado o botão cancelar
			if (callback != null) {
				callback.callItemList();
			}
			return true;
		}
		
		return false;
	}

	public interface CadastroItemCallback {

		public void callItemList();

	}
}
