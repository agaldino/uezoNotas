package br.com.uezonotas;

/*
 * Autor: André Galdino da Silveira
 * 
 * Teste de conectividade com a internet.
 * */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConect {
	public static boolean Conectado(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info == null){
			return false;
		}else{
			return true;
		}

	}
}
