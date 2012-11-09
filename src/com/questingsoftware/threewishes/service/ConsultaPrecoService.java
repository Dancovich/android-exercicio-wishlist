package com.questingsoftware.threewishes.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

import com.questingsoftware.threewishes.R;
import com.questingsoftware.threewishes.model.WishItem;
import com.questingsoftware.threewishes.persistence.DBOpenHelper;

/**
 * 
 * Consulta o preço atual de um determinado item e informa o usuário que o preço
 * mudou.
 * 
 */
public class ConsultaPrecoService extends IntentService {

	private static final String THREAD_NAME = "PriceCheckThread";

	public ConsultaPrecoService() {
		super(THREAD_NAME);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ArrayList<WishItem> itens = DBOpenHelper.selectAll(this);

		if (itens != null) {
			for (WishItem item : itens) {

				if (item.getAtualizarPreco()) {
					// Por enquanto informa o preço atual do item mesmo.
					String text = String
							.format(this
									.getString(R.string.notification_price_change),
									item.getNome(), item.getPrecoMinimo()
											.doubleValue());

					final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

					Builder builder = new Builder(this);
					builder.setWhen(System.currentTimeMillis())
							.setSmallIcon(R.drawable.ic_launcher)
							.setTicker("Olha a Faaaaca!")
							.setContentTitle("Mudança de Preço")
							.setContentText(text)
							.setContentInfo("Content Info")
							.setVibrate(
									new long[] { 0, 100, 100, 200, 100, 200 })
							.setLights(0xff00ff00, 300, 100);

					notificationManager.notify(1, builder.build());
				}

			}
		}
	}
}
